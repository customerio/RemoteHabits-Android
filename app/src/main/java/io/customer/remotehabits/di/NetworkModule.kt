package io.customer.remotehabits.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.customer.remotehabits.Env
import io.customer.remotehabits.service.DispatcherProvider
import io.customer.remotehabits.service.ImplementationDispatcherProvider
import io.customer.remotehabits.service.api.PokeApiHostname
import io.customer.remotehabits.service.api.PokeApiService
import io.customer.remotehabits.service.json.JsonAdapter
import io.customer.remotehabits.ui.MainApplication
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun providePokeApiHostname(): PokeApiHostname = PokeApiHostname(Env.pokeApiEndpoint)

    @Provides
    fun provideDispatcherProvider(): DispatcherProvider = ImplementationDispatcherProvider()

    @Provides
    fun providePokeService(retrofit: Retrofit): PokeApiService = retrofit.create(PokeApiService::class.java)

}