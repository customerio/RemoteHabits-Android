package io.customer.remotehabits.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.customer.remotehabits.Env
import io.customer.remotehabits.service.api.PokeApiHostname
import io.customer.remotehabits.service.api.PokeApiService
import io.customer.remotehabits.service.api.interceptor.DefaultErrorHandlerInterceptor
import io.customer.remotehabits.service.api.interceptor.HttpLoggerInterceptor
import io.customer.remotehabits.service.json.JsonAdapter
import io.customer.remotehabits.service.logger.AppLogger
import io.customer.remotehabits.service.logger.CustomerIOLogger
import io.customer.remotehabits.service.logger.LogcatLogger
import io.customer.remotehabits.service.logger.Logger
import io.customer.remotehabits.service.util.ConnectivityUtil
import kotlinx.coroutines.Dispatchers
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.ExecutorService

@Module
@InstallIn(SingletonComponent::class)
object DependencyModule {

    @Provides
    fun provideLogger(): Logger {
        val loggers: MutableList<Logger> = mutableListOf(CustomerIOLogger())

        if (Env.isDevelopment) {
            loggers.add(LogcatLogger())
        }

        return AppLogger(loggers)
    }

    @Provides
    fun provideOkHttp(@ApplicationContext context: Context, connectivityUtil: ConnectivityUtil, logger: Logger): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggerInterceptor(logger))
        .addInterceptor(DefaultErrorHandlerInterceptor(context, connectivityUtil))
        .build()

    @Provides
    fun provideRetrofit(client: OkHttpClient, hostname: PokeApiHostname): Retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl(hostname.hostname)
        .addConverterFactory(MoshiConverterFactory.create(JsonAdapter.moshi))
        .build()

}