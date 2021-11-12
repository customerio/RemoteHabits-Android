package io.customer.remotehabits.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import io.customer.base.comunication.Action
import io.customer.base.data.ErrorResult
import io.customer.base.data.Success
import io.customer.remotehabits.databinding.ActivityMainBinding
import io.customer.remotehabits.service.logger.LogcatLogger.Companion.TAG
import io.customer.remotehabits.service.util.RandomUtil
import io.customer.sdk.CustomerIO
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var randomUtil: RandomUtil

    private lateinit var binding: ActivityMainBinding

    private var buttonClickCounter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater).apply {
            setContentView(root)
        }

        identifyYouCustomer()
        trackClick()
    }

    private fun trackClick() {
        binding.trackBtn.setOnClickListener {
            CustomerIO.instance().track(
                "button",
                mapOf("click-count" to buttonClickCounter)
            ).enqueue()
            buttonClickCounter++
        }
    }

    private val outputCallback = Action.Callback<Unit> { result ->
        when (result) {
            is ErrorResult -> Log.v("ErrorResult", result.error.getDisplayMessage())
            is Success -> {
                Log.v("Success", "Success")
                registerToken()
            }
        }
    }

    private fun identifyYouCustomer() {
        binding.identifyBtn.setOnClickListener {
            var identifier = binding.identifyEd.text.toString()

            // in case user doesn't enter an identifier use the default one
            if (identifier.isEmpty()) {
                identifier = "remote-habits"
            }

            CustomerIO.instance().identify(identifier = identifier).enqueue(outputCallback)
        }
    }

    private fun registerToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(
            OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                    return@OnCompleteListener
                }

                // Get new FCM registration token
                val token = task.result

                Log.d(TAG, "Token: $token")
                CustomerIO.instance().registerDeviceToken(token).enqueue()
            }
        )
    }
}
