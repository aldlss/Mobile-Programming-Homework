package cn.suwako.speedrun.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "run_data")
data class RunData (
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    @ColumnInfo(name = "user_id")
    val userId: String,
    @ColumnInfo(name = "run_time")
    val runTime: String,
    @ColumnInfo(name = "run_date")
    val runDate: String,
    @ColumnInfo(name = "run_location")
    val runLocation: String?,
    @ColumnInfo(name = "run_distance")
    val runDistance: String,
    @ColumnInfo(name = "run_pace")
    val runPace: String?,
    @ColumnInfo(name = "run_calories")
    val runCalories: String?,
    @ColumnInfo(name = "run_steps")
    val runSteps: String?,
)