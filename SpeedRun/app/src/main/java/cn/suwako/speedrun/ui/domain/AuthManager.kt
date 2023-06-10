package cn.suwako.speedrun.ui.domain

import android.content.SharedPreferences
import cn.suwako.speedrun.MainActivity.Companion.database
import cn.suwako.speedrun.data.local.entities.User
import cn.suwako.speedrun.utils.getMd5Digest

class AuthManager(private val sharedPref: SharedPreferences) {

    private val isLoggedInKey = "isLoggedIn"
    private val loggedInUserIdKey = "loggedInUserId"

    fun getIsLoggedIn(): Boolean {
        return sharedPref.getBoolean(isLoggedInKey, false)
    }
    fun getUserId(): String {
        return sharedPref.getString(loggedInUserIdKey, "") ?: ""
    }

    // 记得等结束（
    suspend fun loginByIdPw(userId: String, password: String): Boolean {
        val user = database.userDao().getUserById(userId) ?: return false
        getMd5Digest(password).let {
            if (it != user.password) {
                return false
            }
            with(sharedPref.edit()) {
                putBoolean(isLoggedInKey, true)
                putString(loggedInUserIdKey, userId)
                apply()
            }
            return true
        }
    }

    suspend fun registerByIdPw(userId: String, password: String): Boolean {
        val user = database.userDao().getUserById(userId)
        if (user != null) {
            return false
        }
        getMd5Digest(password).let {
            database.userDao().insertAll(User(userId, userId, it))
        }
        return true
    }

    fun logout(): Boolean {
        with(sharedPref.edit()) {
            putBoolean(isLoggedInKey, false)
            putString(loggedInUserIdKey, "")
            apply()
        }
        return true
    }
}