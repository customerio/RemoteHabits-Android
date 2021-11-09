package io.customer.remotehabits.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import io.customer.base.comunication.Action
import io.customer.base.data.ErrorResult
import io.customer.base.data.Success
import io.customer.remotehabits.databinding.ActivityMainBinding
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
            ).enqueue(outputCallback)
            buttonClickCounter++
        }
    }

    private val outputCallback = Action.Callback<Unit> { result ->
        when (result) {
            is ErrorResult -> Log.v("ErrorResult", result.error.getDisplayMessage())
            is Success -> Log.v("Success", "Success")
        }
    }

    private fun identifyYouCustomer() {
        CustomerIO.instance()
            .identify(
                identifier = "remote-habits"
            ).enqueue(outputCallback)
    }
}
