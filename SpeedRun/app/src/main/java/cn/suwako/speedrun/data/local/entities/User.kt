package cn.suwako.speedrun.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User (
    @PrimaryKey
    val id: String,
    @ColumnInfo(name = "nickname")
    val nickname: String,
    @ColumnInfo(name = "password")
    val password: String,
    @ColumnInfo(name = "email")
    val email: String? = null,
    @ColumnInfo(name = "phone")
    val phone: String? = null,
    @ColumnInfo(name = "avatar")
    val avatar: String? = null,
    @ColumnInfo(name = "address")
    val address: String? = null,
)