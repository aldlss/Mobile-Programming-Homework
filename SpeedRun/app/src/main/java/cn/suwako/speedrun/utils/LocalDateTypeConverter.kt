package cn.suwako.speedrun.utils

import androidx.room.TypeConverter
import java.time.LocalDate

class LocalDateTypeConverter {
    @TypeConverter
    fun localDateToEpochDay(date: LocalDate): Int {
        return date.toEpochDay().toInt()
    }

    @TypeConverter
    fun epochDayToLocalDate(day: Int): LocalDate {
        return LocalDate.ofEpochDay(day.toLong())
    }
}