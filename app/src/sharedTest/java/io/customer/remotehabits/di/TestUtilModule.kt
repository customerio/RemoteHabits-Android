package io.customer.remotehabits.di

import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import io.customer.remotehabits.service.util.RandomUtil
import javax.inject.Singleton
import org.mockito.Mockito

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [UtilModule::class]
)
object TestUtilModule {

    @Provides
    @Singleton
    fun provideRandomUtil(): RandomUtil = Mockito.mock(RandomUtil::class.java)
}
