package cn.suwako.speedrun.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import cn.suwako.speedrun.utils.LocalDateTypeConverter
import cn.suwako.speedrun.utils.LocalTimeTypeConverter
import java.time.LocalDate
import java.time.LocalTime

@Entity(tableName = "run_data")
// 略怪，这个必须放在类上，放在字段上好像不行
@TypeConverters(LocalDateTypeConverter::class, LocalTimeTypeConverter::class)
data class RunData (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "user_id")
    val userId: String,

    @ColumnInfo(name = "run_time")
    val runTime: LocalTime,

    @ColumnInfo(name = "run_date")
    val runDate: LocalDate,

    @ColumnInfo(name = "run_location")
    val runLocation: String,

    @ColumnInfo(name = "run_distance")
    val runDistance: Int,

    @ColumnInfo(name = "run_speed")
    val runSpeed: Float,

    @ColumnInfo(name = "run_calories")
    val runCalories: Float,

    @ColumnInfo(name = "run_steps")
    val runSteps: Int,

    @ColumnInfo(name = "run_picture")
    val runPicture: String? = null,
)