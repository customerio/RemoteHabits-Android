package io.customer.remotehabits.data.persistance

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import io.customer.remotehabits.R
import io.customer.remotehabits.data.models.Habit
import io.customer.remotehabits.data.models.User
import io.customer.remotehabits.data.stubs.HabitsStub
import io.customer.remotehabits.utils.ioThread

@Database(entities = [User::class, Habit::class], version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    abstract fun habitDao(): HabitDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                context.getString(R.string.database)
            )
                // prepopulate the habit database after onCreate was called
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        // insert the data on the IO Thread
                        ioThread {
                            getInstance(context).habitDao().insert(*HabitsStub.getHabits(context))
                        }
                    }
                })
                .fallbackToDestructiveMigration()
                .build()
    }
}
