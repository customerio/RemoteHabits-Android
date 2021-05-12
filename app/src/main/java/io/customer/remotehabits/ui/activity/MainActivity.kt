package io.customer.remotehabits.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import dagger.hilt.android.AndroidEntryPoint
import io.customer.remotehabits.R
import io.customer.remotehabits.service.logger.LogcatLogger
import io.customer.remotehabits.service.logger.Logger
import io.customer.remotehabits.viewmodel.PokemonViewModel
import io.customer.sdk.ExampleSdkFile
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val pokemonViewModel: PokemonViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        ExampleSdkFile().add(1, 1)
    }

    override fun onStart() {
        super.onStart()

        pokemonViewModel.getPokemon("ditto") { apiResult ->
            if (apiResult.isSuccess) Log.d(LogcatLogger.TAG, apiResult.bodyOrThrow().toString())
            else {
                AlertDialog.Builder(this).apply {
                    setMessage(apiResult.failureOrThrow().message)
                    show()
                }
            }
        }
    }

}