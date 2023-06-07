package cn.suwako.speedrun.data.local.dao

import androidx.room.*
import cn.suwako.speedrun.data.local.entities.RunData

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
}