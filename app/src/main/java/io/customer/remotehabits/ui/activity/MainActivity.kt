package io.customer.remotehabits.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import io.customer.base.comunication.Action
import io.customer.base.data.ErrorResult
import io.customer.base.data.Success
import io.customer.remotehabits.databinding.ActivityMainBinding
import io.customer.sdk.CustomerIO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var buttonClickCounter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater).apply {
//            setContentView(root)
        }

        setupListeners()
    }

    private fun setupListeners() {
        binding.trackBtn.setOnClickListener {
            CustomerIO.instance().track(
                "button",
                mapOf("click-count" to buttonClickCounter)
            ).enqueue(outputCallback)
            buttonClickCounter++
        }

        binding.identifyBtn.setOnClickListener {
            var identifier = binding.identifyEd.text.toString()

            // in case user doesn't enter an identifier use the default one
            if (identifier.isEmpty()) {
                identifier = "remote-habits"
            }

            identify(identifier)
        }
    }

    private val outputCallback = Action.Callback<Unit> { result ->
        when (result) {
            is ErrorResult -> Log.v("ErrorResult", result.error.getDisplayMessage())
            is Success -> Log.v("Success", "Success")
        }
    }

    private fun identify(identifier: String) {
        CoroutineScope(Dispatchers.IO).launch {
            // unregister token from any previously identified customer
            CustomerIO.instance().deleteDeviceToken().execute()

            // identify a customer
            val result = CustomerIO.instance().identify(identifier = identifier)
                .execute()
            if (result is Success) {
                // once identified register the token with the newly identified customer
                registerToken()
            }
        }
    }

    private fun registerToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(
            OnCompleteListener { task ->
                val TAG = MainActivity::class.qualifiedName

                if (!task.isSuccessful) {
                    Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                    return@OnCompleteListener
                }

                // Get new FCM registration token
                val token = task.result

                Log.d(TAG, "Token: $token")
                CustomerIO.instance().registerDeviceToken(token).enqueue(outputCallback)
            }
        )
    }
}
