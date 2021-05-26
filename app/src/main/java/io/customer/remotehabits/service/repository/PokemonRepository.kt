package io.customer.remotehabits.service.repository

import io.customer.remotehabits.service.DispatcherProvider
import io.customer.remotehabits.service.api.ApiResult
import io.customer.remotehabits.service.api.PokemonApi
import io.customer.remotehabits.service.vo.PokemonVo
import javax.inject.Inject
import kotlinx.coroutines.withContext

class PokemonRepository @Inject constructor(
    private val api: PokemonApi,
    private val dispatcherProvider: DispatcherProvider
) {

    suspend fun getPokemon(name: String): ApiResult<PokemonVo> = withContext(dispatcherProvider.io()) {
        api.getPokemon(name)
    }
}
