package io.customer.remotehabits.di

import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import io.customer.remotehabits.service.DispatcherProvider
import io.customer.remotehabits.service.api.PokeApiService
import javax.inject.Singleton
import org.mockito.Mockito

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [NetworkModule::class]
)
object TestNetworkModule {

    @Provides
    @Singleton
    fun provideDispatcherProvider(): DispatcherProvider = Mockito.mock(DispatcherProvider::class.java)

    @Provides
    @Singleton
    fun providePokemonService(): PokeApiService = Mockito.mock(PokeApiService::class.java)
}
