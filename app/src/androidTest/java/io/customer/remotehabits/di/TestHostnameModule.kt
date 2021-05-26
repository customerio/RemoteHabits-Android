package io.customer.remotehabits.di

import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import io.customer.remotehabits.MockWebServer
import io.customer.remotehabits.service.api.PokeApiHostname
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [HostnameModule::class]
)
object TestHostnameModule {

    @Provides
    @Singleton
    fun providePokeHostname(): PokeApiHostname = PokeApiHostname(MockWebServer.url)
}
