package io.customer.remotehabits.data.persistance

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import io.customer.remotehabits.data.models.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun observeAll(): Flow<List<User>>

    @Query("SELECT * FROM user WHERE email IN (:emails)")
    fun observeAllByEmail(emails: Array<String>): Flow<List<User>>

    @Query("SELECT * FROM user WHERE name LIKE :name LIMIT 1")
    fun findByName(name: String): User

    @Query("SELECT COUNT(*) FROM user")
    fun getUserCount(): Long

    @Insert
    suspend fun insert(vararg users: User)

    @Delete
    suspend fun delete(user: User)
}
