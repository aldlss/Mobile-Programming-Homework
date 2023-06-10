package cn.suwako.speedrun.data.local.dao

import androidx.room.*
import cn.suwako.speedrun.data.local.entities.RunData
import kotlinx.coroutines.flow.Flow

@Dao
interface RunDataDao {
    @Insert
    suspend fun insertAll(vararg runData: RunData)

    @Update
    suspend fun updateUsers(vararg runDatas: RunData)

    @Delete
    suspend fun delete(runData: RunData)

    @Query("SELECT * FROM run_data")
    suspend fun getAll(): List<RunData>

    @Query("SELECT * FROM run_data WHERE id = :id")
    suspend fun getRunDataById(id: Int): RunData?

    @Query("SELECT * FROM run_data WHERE user_id = :userId")
    suspend fun getRunDataByUserId(userId: String): List<RunData>

    @Query("SELECT * FROM run_data WHERE user_id = :userId")
    fun loadRunDataByUserId(userId: String): Flow<List<RunData>>
}