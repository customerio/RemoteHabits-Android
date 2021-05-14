package io.customer.remotehabits.di

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.customer.remotehabits.service.util.AppRandomUtil
import io.customer.remotehabits.service.util.RandomUtil

@Module
@InstallIn(SingletonComponent::class)
object UtilModule {

    @Provides
    fun provideRandomUtil(): RandomUtil = AppRandomUtil()

}