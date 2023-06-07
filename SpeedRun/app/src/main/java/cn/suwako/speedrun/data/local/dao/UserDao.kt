package cn.suwako.speedrun.data.local.dao

import androidx.room.*
import cn.suwako.speedrun.data.local.entities.User

@Dao
interface UserDao {
    @Insert
    suspend fun insertAll(vararg users: User)

    @Update
    suspend fun updateUsers(vararg users: User)

    @Delete
    suspend fun delete(user: User)

    @Query("SELECT * FROM users")
    suspend fun getAll(): List<User>

    @Query("SELECT * FROM users WHERE id = :userId")
    suspend fun getUserById(userId: String): User?
}