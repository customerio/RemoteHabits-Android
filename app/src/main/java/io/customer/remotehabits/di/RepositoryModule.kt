package io.customer.remotehabits.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import io.customer.remotehabits.data.persistance.HabitDao
import io.customer.remotehabits.data.persistance.UserDao
import io.customer.remotehabits.data.repositories.HabitRepository
import io.customer.remotehabits.data.repositories.HabitRepositoryImpl
import io.customer.remotehabits.data.repositories.UserRepository
import io.customer.remotehabits.data.repositories.UserRepositoryImpl

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    @ViewModelScoped
    fun provideUserRepository(userDao: UserDao): UserRepository {
        return UserRepositoryImpl(userDao = userDao)
    }

    @Provides
    @ViewModelScoped
    fun provideHabitRepository(habitDao: HabitDao): HabitRepository {
        return HabitRepositoryImpl(habitDao = habitDao)
    }
}
