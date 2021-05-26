package io.customer.remotehabits.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import io.customer.remotehabits.databinding.ActivityMainBinding
import io.customer.remotehabits.service.logger.LogcatLogger
import io.customer.remotehabits.service.util.RandomUtil
import io.customer.remotehabits.viewmodel.PokemonViewModel
import io.customer.sdk.ExampleSdkFile
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val pokemonViewModel: PokemonViewModel by viewModels()

    @Inject lateinit var randomUtil: RandomUtil

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater).apply {
            setContentView(root)
        }

        ExampleSdkFile().add(1, 1)
    }

    override fun onStart() {
        super.onStart()

        /**
         * Note: The code below is just to test that the testing development environment is setup correctly. There are some bad practices here that are known. All the code will be removed soon anyway.
         */
        binding.pokemonTextview.text = "Loading a surprise..."

        pokemonViewModel.getPokemon("ditto") { apiResult ->
            if (apiResult.isSuccess) {
                Log.d(LogcatLogger.TAG, apiResult.bodyOrThrow().toString())

                val pokemon = apiResult.bodyOrThrow()

                val shinyChances = randomUtil.randomInt(0, 10)

                if (shinyChances == 0) {
                    binding.pokemonImage.load(pokemon.sprites.front_shiny)
                    binding.pokemonTextview.text = "You caught a Pokemon!\n?@&* YOU CAUGHT A RARE SHINY?!\n${pokemon.name}"
                } else {
                    binding.pokemonImage.load(pokemon.sprites.front_default)
                    binding.pokemonTextview.text = "You caught a Pokemon!\n${pokemon.name}"
                }
            } else {
                AlertDialog.Builder(this).apply {
                    setMessage(apiResult.failureOrThrow().message)
                    show()
                }
            }
        }
    }
}
