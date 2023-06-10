package cn.suwako.speedrun.ui.domain

import android.Manifest
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

class PermissionManager(private val activity: ComponentActivity) {

    private val _getPermissionSuccess: Channel<Boolean> = Channel()
    val getPermissionSuccess = _getPermissionSuccess.receiveAsFlow()

    // refer to https://developer.android.google.cn/training/basics/intents/result?hl=zh-cn#kotlin
    private val cameraPermissionLauncher = activity.registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            Toast.makeText(activity, "相机权限授权成功", Toast.LENGTH_SHORT).show()
            _getPermissionSuccess.trySend(true)
        } else {
            Toast.makeText(activity, "相机权限授权失败", Toast.LENGTH_SHORT).show()
            _getPermissionSuccess.trySend(false)
        }
    }

    private val storagePermissionLauncher = activity.registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            Toast.makeText(activity, "读取权限授权成功", Toast.LENGTH_SHORT).show()
            _getPermissionSuccess.trySend(true)
        } else {
            Toast.makeText(activity, "读取权限授权失败", Toast.LENGTH_SHORT).show()
            _getPermissionSuccess.trySend(false)
        }
    }

    fun checkCameraPermission(): Boolean {
        val permission = Manifest.permission.CAMERA
        val granted = PackageManager.PERMISSION_GRANTED
        return ContextCompat.checkSelfPermission(activity, permission) == granted
    }

    fun checkStoragePermission(): Boolean {
        val permission = Manifest.permission.READ_EXTERNAL_STORAGE
        val granted = PackageManager.PERMISSION_GRANTED
        return ContextCompat.checkSelfPermission(activity, permission) == granted
    }

    fun requestCameraPermission() {
        val permission = Manifest.permission.CAMERA
        cameraPermissionLauncher.launch(permission)
    }

    fun requestStoragePermission() {
        val permission = Manifest.permission.READ_EXTERNAL_STORAGE
        storagePermissionLauncher.launch(permission)
    }
}
