package cn.suwako.speedrun.ui.viewmodel

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cn.suwako.speedrun.MainActivity
import cn.suwako.speedrun.data.local.entities.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

class AccountDetailViewModel: ViewModel() {

    private var _userId = ""
    private var originAvatar : Bitmap? = null
    var user by mutableStateOf(User("5", "satori", "0"))
    var avatar by mutableStateOf<Bitmap?>(null)

    private val _saveSuccess: Channel<Boolean> = Channel()
    val saveSuccess = _saveSuccess.receiveAsFlow()

    // 之前 ViewModel 的 init 里写这个结果报了不能在界面初始化前调用 state 的错
    // 大概放这里应该可以？
    fun loadUser(userId: String) {
        viewModelScope.launch {
            _userId = userId
            user = withContext(Dispatchers.IO) {
                val content = MainActivity.database.userDao().getUserById(userId)!!
                originAvatar = content.avatar?.let { BitmapFactory.decodeFile(it) }
                content
            }
            avatar = originAvatar
        }
    }

    fun updateUser(fileDir: File) {
        viewModelScope.launch {
            try {
                avatar?.let { bitmap ->
                    if (bitmap != originAvatar) {
                        val avatarFileDir = File(fileDir, "avatar")
                        if (!avatarFileDir.exists()) {
                            avatarFileDir.mkdir()
                        }
                        val avatarFile = File(avatarFileDir, "$_userId.jpg")

                        withContext(Dispatchers.IO) {

                            avatarFile.outputStream().use {
                                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
                            }
                        }

                        user = user.copy(avatar = avatarFile.absolutePath)
                    }
                }

                withContext(Dispatchers.IO) {
                    MainActivity.database.userDao().updateUsers(user)
                }
                _saveSuccess.send(true)
            }
            catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}