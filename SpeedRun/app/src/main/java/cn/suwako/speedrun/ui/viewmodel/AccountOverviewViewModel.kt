package cn.suwako.speedrun.ui.viewmodel

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cn.suwako.speedrun.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AccountOverviewViewModel: ViewModel() {

    var nickname by mutableStateOf("satori")
    var avatar by mutableStateOf<Bitmap?>(null)

    fun loadUser(userId: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                MainActivity.database.userDao().loadUserById(userId).collect {
                    it!!.let { user ->
                        avatar = user.avatar?.let { avatarPath -> BitmapFactory.decodeFile(avatarPath) }
                        nickname = user.nickname
                    }
                }
            }
        }
    }

}