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

class LoginViewModel : ViewModel() {
    var userId by mutableStateOf("")
    var password by mutableStateOf("")

    // refer to https://juejin.cn/post/7031726493906829319
    private val _loginSuccess: Channel<Boolean> = Channel()
    val loginSuccess = _loginSuccess.receiveAsFlow()

    fun login() {
        viewModelScope.launch {
            val success = MainActivity.authManager.loginByIdPw(userId, password)
            _loginSuccess.send(success)
        }
    }
}