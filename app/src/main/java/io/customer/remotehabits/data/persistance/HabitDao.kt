package io.customer.remotehabits.data.persistance

import androidx.room.*
import io.customer.remotehabits.data.models.Habit
import io.customer.remotehabits.data.models.HabitType
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitDao {
    @Query("SELECT * FROM habit")
    fun observeAll(): Flow<List<Habit>>

    @Query("SELECT * FROM habit WHERE type IN (:types)")
    fun observeAllByType(types: Array<HabitType>): Flow<List<Habit>>

    @Query("SELECT * FROM habit WHERE name LIKE :name LIMIT 1")
    fun findByName(name: String): Habit

    @Update
    suspend fun updateHabits(vararg habit: Habit)

    @Insert
    fun insert(vararg habit: Habit)

    @Delete
    fun delete(habit: Habit)
}
