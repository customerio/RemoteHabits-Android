package io.customer.remotehabits.service.vo

import android.net.Uri
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PokemonSpeciesVo(
    val name: String,
    val url: Uri
)