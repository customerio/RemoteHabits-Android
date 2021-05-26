package io.customer.remotehabits.service.api

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import io.customer.remotehabits.R
import io.customer.remotehabits.service.error.network.ApiDownException
import io.customer.remotehabits.service.logger.Logger
import io.customer.remotehabits.service.vo.PokemonVo
import javax.inject.Inject

/**
 * Class that encapsulates http requests while also handling problems that can occur for each.
 *
 * The point of this class is to contain a function for all endpoints of an API. Each function does all of the deserialization and extra error handling. Let's say 1 endpoint can return a 400 error while others do not. The function for that certain endpoint will handle the 400 response for you.
 *
 * Each function returns a Kotlin Result type. This means that Rx onError will not be called. Either the Http function was successful (200-300 response with a deserialized VO object returned) or there was an error. The Result.failure exception will have a human readable error message that you can display to the user.
 *
 * If there is an error, this class will handle it. If it's a network error, it will simply give you that message so you can display that to the user. If it's a user error, the error will be optionally parsed from the response to be shown for the user and the UI can handle the error if it wants to. If it's a developer error, the error will be logged to notify the developer team to fix it.
 */
class PokemonApi @Inject constructor(
    @ApplicationContext private val context: Context,
    logger: Logger,
    private val service: PokeApiService,
) : Api(context, logger) {

    suspend fun getPokemon(name: String): ApiResult<PokemonVo> {
        return request({
            service.getPokemon(name)
        })
    }

    override fun <T> processFailedStatusCodes(response: ProcessedResponse<String>): ApiResult<T>? {
        return when (response.statusCode) {
            in 500..600 -> ApiResult.failure(ApiDownException(context.getString(R.string.api_down_message)))
            else -> null
        }
    }
}
