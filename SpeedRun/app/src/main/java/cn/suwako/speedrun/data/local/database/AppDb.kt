package cn.suwako.speedrun.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import cn.suwako.speedrun.data.local.dao.RunDataDao
import cn.suwako.speedrun.data.local.dao.UserDao
import cn.suwako.speedrun.data.local.entities.RunData
import cn.suwako.speedrun.data.local.entities.User

@Database(entities = [User::class, RunData::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun runDataDao(): RunDataDao
}