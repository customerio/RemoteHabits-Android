package io.customer.remotehabits.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.customer.remotehabits.utils.Logger
import io.customer.remotehabits.utils.RHLogger
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class BaseModule {
    @Singleton
    @Binds
    internal abstract fun provideLogger(bind: RHLogger): Logger
}
