package io.customer.remotehabits.data.persistance

import androidx.room.Database
import androidx.room.RoomDatabase
import io.customer.remotehabits.data.models.User

@Database(entities = [User::class], version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
}
