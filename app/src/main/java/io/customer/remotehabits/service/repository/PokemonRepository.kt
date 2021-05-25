package io.customer.remotehabits.service.repository

import io.customer.remotehabits.service.DispatcherProvider
import io.customer.remotehabits.service.api.ApiResult
import io.customer.remotehabits.service.api.PokemonApi
import io.customer.remotehabits.service.vo.PokemonVo
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PokemonRepository @Inject constructor(
    private val api: PokemonApi,
    private val dispatcherProvider: DispatcherProvider
) {

    suspend fun getPokemon(name: String): ApiResult<PokemonVo> = withContext(dispatcherProvider.io()) {
        api.getPokemon(name)
    }

}