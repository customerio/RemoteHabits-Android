package io.customer.remotehabits.service.vo

import com.squareup.moshi.JsonClass

// example response: https://pokeapi.co/api/v2/pokemon/ditto
@JsonClass(generateAdapter = true)
data class PokemonVo(
    val name: String,
    val species: PokemonSpeciesVo
)