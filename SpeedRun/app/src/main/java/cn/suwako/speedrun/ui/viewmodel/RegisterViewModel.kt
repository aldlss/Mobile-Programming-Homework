package cn.suwako.speedrun.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cn.suwako.speedrun.MainActivity
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {
    var username by mutableStateOf("")
    var password by mutableStateOf("")
    var confirmPassword by mutableStateOf("")

    private val _registerSuccess: Channel<Boolean> = Channel()
    val registerSuccess = _registerSuccess.receiveAsFlow()

    fun register() {
        viewModelScope.launch {
            if (password != confirmPassword || username == "" || password == "") {
                _registerSuccess.send(false)
                return@launch
            }
            val success = MainActivity.authManager.registerByIdPw(username, password)
            _registerSuccess.send(success)
        }
    }
}