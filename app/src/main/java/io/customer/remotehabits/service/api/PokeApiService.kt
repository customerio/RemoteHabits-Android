package io.customer.remotehabits.service.api

import io.customer.remotehabits.service.vo.PokemonVo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PokeApiService {

    @GET("pokemon/{pokemon_name}")
    suspend fun getPokemon(@Path("pokemon_name") pokemonName: String): Response<PokemonVo>
}
