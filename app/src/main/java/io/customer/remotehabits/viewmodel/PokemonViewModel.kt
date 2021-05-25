package io.customer.remotehabits.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.customer.remotehabits.service.api.ApiResult
import io.customer.remotehabits.service.repository.PokemonRepository
import io.customer.remotehabits.service.vo.PokemonVo
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonViewModel @Inject constructor(private val pokemonRepository: PokemonRepository): ViewModel() {

    fun getPokemon(name: String, callback: (result: ApiResult<PokemonVo>) -> Unit) {
        viewModelScope.launch {
            callback(pokemonRepository.getPokemon(name))
        }
    }

}