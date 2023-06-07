//package cn.suwako.speedrun.ui.viewmodel
//
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.setValue
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import cn.suwako.speedrun.LocalSharedPreferences
//import cn.suwako.speedrun.MainActivity
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.launch
//
//class LoginViewModel : ViewModel() {
//    var username: String by mutableStateOf("")
//    var password: String by mutableStateOf("")
//
//    private val _navigateToScreen = MutableStateFlow(false)
//    val navigateToScreen: StateFlow<Boolean> = _navigateToScreen
//
//    private fun loginSucceed() {
//        _navigateToScreen.value = true
//    }
//
//    fun login(isLoggedInKey: String, loggedInUserIdKey: String) {
//        viewModelScope.launch {
//            loginAsync(isLoggedInKey, loggedInUserIdKey)
//        }
//    }
//
//    private suspend fun loginAsync(isLoggedInKey: String, loggedInUserIdKey: String) {
//        val user = MainActivity.database.userDao().getUserById(username)
//        if (user == null || user.password == password) {
//            val sharedPref = LocalSharedPreferences.current
//            with(sharedPref.edit()) {
//                putBoolean(isLoggedInKey, true)
//                putString(loggedInUserIdKey, username)
//                apply()
//            }
//            loginSucceed()
//        }
//    }
//}