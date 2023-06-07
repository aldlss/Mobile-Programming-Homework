package cn.suwako.speedrun.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cn.suwako.speedrun.MainActivity
import cn.suwako.speedrun.data.local.entities.User
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {
    var username by mutableStateOf("")
    var password by mutableStateOf("")
    var confirmPassword by mutableStateOf("")

    fun register() {
        viewModelScope.launch {
            registerAsync()
        }
    }

    private suspend fun registerAsync() {
        val user = MainActivity.database.userDao().getUserById(username)
        if(user == null) {
            val newUser = User(username, username, password)
            MainActivity.database.userDao().insertAll(newUser)
        }
    }
}