package io.customer.remotehabits.data.repositories

import android.content.Context
import io.customer.remotehabits.data.models.Habit
import io.customer.remotehabits.data.models.HabitType
import io.customer.remotehabits.data.persistance.HabitDao
import io.customer.remotehabits.data.stubs.HabitsStub
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

interface HabitRepository {
    fun getHabits(): Flow<List<Habit>>
    suspend fun getHabit(type: HabitType): Flow<Habit>
    suspend fun updateHabit(habit: Habit)
    suspend fun reset(context: Context)
}

class HabitRepositoryImpl(private val habitDao: HabitDao) : HabitRepository {

    override fun getHabits(): Flow<List<Habit>> {
        return habitDao.observeAll().flowOn(Dispatchers.IO)
    }

    override suspend fun getHabit(type: HabitType): Flow<Habit> {
        return habitDao.observeByType(type = type)
    }

    override suspend fun updateHabit(habit: Habit) {
        habitDao.updateHabits(habit)
    }

    override suspend fun reset(context: Context) {
        habitDao.updateHabits(*HabitsStub.getHabits(context))
    }
}
