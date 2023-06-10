package cn.suwako.speedrun.utils

import androidx.room.TypeConverter
import java.time.LocalTime

class LocalTimeTypeConverter {
    @TypeConverter
    fun localTimeToSeconds(time: LocalTime): Int {
        return time.toSecondOfDay()
    }

    @TypeConverter
    fun secondsToLocalTime(seconds: Int): LocalTime {
        return LocalTime.ofSecondOfDay(seconds.toLong())
    }
}