package io.customer.remotehabits.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.customer.remotehabits.Env
import io.customer.remotehabits.service.Dispatchers
import io.customer.remotehabits.service.api.PokeApiHostname
import io.customer.remotehabits.ui.MainApplication

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun providePokeApiHostname(): PokeApiHostname = PokeApiHostname(Env.pokeApiEndpoint)

    @Provides
    fun provideDispatchers(): Dispatchers = Dispatchers(kotlinx.coroutines.Dispatchers.IO)
}