package io.customer.remotehabits.service.api.interceptor

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import io.customer.remotehabits.R
import io.customer.remotehabits.service.error.network.NoInternetConnectionException
import io.customer.remotehabits.service.util.ConnectivityUtil
import javax.inject.Inject
import okhttp3.Interceptor
import okhttp3.Response

class DefaultErrorHandlerInterceptor @Inject constructor(
    @ApplicationContext private val context: Context,
    private val connectivityUtil: ConnectivityUtil
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isOnline()) throw NoInternetConnectionException(context.getString(R.string.no_internet_connection_error_message))

        val request = chain.request()

        return chain.proceed(request)
    }

    private fun isOnline(): Boolean = connectivityUtil.isNetworkAvailable()
}
