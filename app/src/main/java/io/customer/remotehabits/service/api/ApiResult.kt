package io.customer.remotehabits.service.api

data class ApiResult<T>(
    val body: T?,
    val code: Int?,
    val failureError: Throwable?
) {

    companion object {
        fun <T> success(code: Int, body: T) = ApiResult(body, code, null)
        fun <T> failure(error: Throwable) = ApiResult<T>(null, null, error)
    }

    val isSuccess: Boolean = body != null && code != null
    val isFailure: Boolean = !isSuccess

    fun bodyOrThrow(): T = body!!
    fun failureOrThrow(): Throwable = failureError!!
}
