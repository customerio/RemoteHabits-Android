package io.customer.remotehabits.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import io.customer.remotehabits.appinitializers.AppInitializer
import io.customer.remotehabits.appinitializers.CustomerIOInitializer
import io.customer.remotehabits.appinitializers.FirebaseMessagingInitializer
import io.customer.remotehabits.appinitializers.TimberInitializer

@InstallIn(SingletonComponent::class)
@Module
abstract class AppModuleBinds {

    @Binds
    @IntoSet
    abstract fun provideCustomerIOInitializer(bind: CustomerIOInitializer): AppInitializer

    @Binds
    @IntoSet
    abstract fun provideTimberInitializer(bind: TimberInitializer): AppInitializer

    @Binds
    @IntoSet
    abstract fun provideFirebaseMessagingInitializer(bind: FirebaseMessagingInitializer): AppInitializer
}
