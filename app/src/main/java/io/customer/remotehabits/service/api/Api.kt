package io.customer.remotehabits.service.api

import android.content.Context
import io.customer.remotehabits.R
import io.customer.remotehabits.service.error.DeveloperError
import io.customer.remotehabits.service.error.network.BadNetworkConnectionException
import io.customer.remotehabits.service.error.network.NoInternetConnectionException
import io.customer.remotehabits.service.error.network.UnhandledHttpResponseException
import io.customer.remotehabits.service.logger.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.io.IOException

/**
 * Class that encapsulates http requests while also handling problems that can occur for each.
 *
 * This class is meant to be subclassed where each function is for an endpoint of the API. Each function does all of the deserialization and extra error handling for that specific endpoint.
 */
abstract class Api constructor(
    private val context: Context,
    private val logger: Logger
) {

    /**
     * Performs the bulk of the network call for you.
     *
     * The flow of a network call:
     * - Perform http call
     *   - If error is thrown, the http call did not actually perform. No status got returned from server. It could be no Internet connection, bad Internet connection, or some other error that could be a bug in the app.
     *     - When an error happens, see if it's a network issue. If it is, return that result to the user to display to them.
     *     - If it's not a network issue, it's a developer issue. Log the exception to notify the team to fix the issue.
     *   - If no error, the http call did perform. A response came back from the server.
     *     - Give the specific API subclass an opportunity to handle the status code and return a result back to the user.
     *     - If the response is a 200-300 response, it is successful and the caller can help transform the response body into an object for you and then this function returns that result.
     *
     * @param extraErrorHandling If an endpoint returns back a status code > 300, this function has the opportunity to handle it, first. A good example is if an endpoint returns a 404. Some APIs use this for security reasons to determine that you are not authenticated to access a resource while most of the time a 404 indicates that a typo more then likely occurred. This function is called before API subclass has the opportunity to handle the server response.
     * @param extraSuccessHandling Gives the endpoint an opportunity to handle a successful result.
     */
    suspend fun <HttpResponse> request(call: suspend () -> Response<HttpResponse>, extraErrorHandling: ((ProcessedResponse<String>) -> Throwable?)? = null, extraSuccessHandling: ((ProcessedResponse<HttpResponse>) -> HttpResponse?)? = null): ApiResult<HttpResponse> {
        return try {
            val response = call()

            if (response.isSuccessful) processSuccessful(response, extraSuccessHandling)
            else processFailure(response, extraErrorHandling)
        } catch (error: Throwable) {
            processNoResponse(error)
        }
    }

    private fun <HttpResponse> processNoResponse(error: Throwable): ApiResult<HttpResponse> {
        return when (error) {
            // No Internet
            is NoInternetConnectionException -> ApiResult.failure(error)
            // Bad network connection
            is IOException -> ApiResult.failure(BadNetworkConnectionException(context.getString(R.string.bad_internet_connection_error_message)))
            // Some other error. Have the developer check it out. According to Retrofit's result.error() Javadoc, if the error is not an instance of IOException, it's a programming error and should be looked at. Throw it so we can see it and fix it.
            else -> {
                logger.errorOccurred(error)

                return ApiResult.failure(DeveloperError(error, context.getString(R.string.developer_error_message)))
            }
        }
    }

    /**
     * Successful == 200...300 status code
     */
    private fun <HttpResponse> processSuccessful(response: Response<HttpResponse>, extraSuccessHandling: ((ProcessedResponse<HttpResponse>) -> HttpResponse?)?): ApiResult<HttpResponse> {
        val processedResponse = ProcessedResponse(response.raw().request.url.toString(), response.raw().request.method, response.code(), response.body())

        extraSuccessHandling?.let { extraSuccessHandling ->
            extraSuccessHandling(processedResponse)?.let { return ApiResult.success(processedResponse.statusCode, it) }
        }

        return ApiResult.success(processedResponse.statusCode, processedResponse.body!!)
    }

    /**
     * Failure == > 300 status code
     */
    private fun <HttpResponse> processFailure(response: Response<HttpResponse>, extraErrorHandling: ((ProcessedResponse<String>) -> Throwable?)?): ApiResult<HttpResponse> {
        val processedResponse = ProcessedResponse(response.raw().request.url.toString(), response.raw().request.method, response.code(), response.errorBody()!!.string())
        extraErrorHandling?.let { extraErrorHandling ->
            extraErrorHandling(processedResponse)?.let { return ApiResult.failure(it) }
        }

        processFailedStatusCodes<HttpResponse>(processedResponse)?.let { return it }

        /**
         * A response came back from the server but we were not expecting it. More then likely, the developer forgot a case in [processFailedStatusCodes] but could be another reason.
         *
         * Make sure the error that we log is useful to the developer so they can fix it (include debug info about the HTTP method). Then, return human readable error back to user. 
         */
        val unhandledHttpResponseError = UnhandledHttpResponseException(processedResponse)
        logger.errorOccurred(unhandledHttpResponseError)
        return ApiResult.failure(DeveloperError(unhandledHttpResponseError, context.getString(R.string.developer_error_message)))
    }

    /**
     * Called when API received a response from the server but was > 300 status code.
     *
     * It's up to you to determine if the response was successful or not. That's why you must return an [ApiResult]. If the response is something you are unaware of, return null from the function. If you do this, the app will consider this a developer error as it's a HTTP response that you have not yet handled. An error will be logged to notify team.
     */
    abstract fun <T> processFailedStatusCodes(response: ProcessedResponse<String>): ApiResult<T>?

}