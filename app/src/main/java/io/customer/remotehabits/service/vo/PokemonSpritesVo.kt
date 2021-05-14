package io.customer.remotehabits.service.vo

import android.net.Uri
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PokemonSpritesVo(
    val front_default: Uri,
    val front_shiny: Uri
)