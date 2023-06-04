package cn.suwako.speedrun.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "password")
    val password: String,
    @ColumnInfo(name = "email")
    val email: String?,
    @ColumnInfo(name = "phone")
    val phone: String?,
    @ColumnInfo(name = "avatar")
    val avatar: String?,
    @ColumnInfo(name = "address")
    val address: String?,
)