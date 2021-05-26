package io.customer.remotehabits.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.customer.remotehabits.Env
import io.customer.remotehabits.service.api.PokeApiHostname

/**
 * Isolate hostnames to their own module to make test override modules easier. Android instrumentation tests do not need to override the networking module so putting hostnames in there will require duplicate code (not DRY).
 */
@Module
@InstallIn(SingletonComponent::class)
object HostnameModule {

    @Provides
    fun providePokeApiHostname(): PokeApiHostname = PokeApiHostname(Env.pokeApiEndpoint)
}
