package io.customer.remotehabits.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.customer.remotehabits.service.DispatcherProvider
import io.customer.remotehabits.service.ImplementationDispatcherProvider
import io.customer.remotehabits.service.api.PokeApiService
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideDispatcherProvider(): DispatcherProvider = ImplementationDispatcherProvider()

    @Provides
    fun providePokeService(retrofit: Retrofit): PokeApiService = retrofit.create(PokeApiService::class.java)

}