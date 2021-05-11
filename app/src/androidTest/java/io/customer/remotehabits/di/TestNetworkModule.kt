package io.customer.remotehabits.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import io.customer.remotehabits.mock.MockWebServer
import io.customer.remotehabits.service.api.GitHubApiHostname
import org.mockito.Mockito
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [NetworkModule::class]
)
object TestNetworkModule {

    @Provides
    @Singleton
    fun provideMockWebserver(): MockWebServer = MockWebServer(okhttp3.mockwebserver.MockWebServer())

    @Provides
    @Singleton
    fun provideGitHubHostname(mockWebServer: MockWebServer): GitHubApiHostname = GitHubApiHostname(mockWebServer.url)

}