package cn.suwako.speedrun.ui.utils

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class PermissionManager(private val activity: ComponentActivity) {

    private val cameraPermissionLauncher = activity.registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        // Handle camera permission result
        if (isGranted) {
            // Permission granted
            // Perform necessary actions
        } else {
            // Permission denied
            // Handle denied permission case
        }
    }

    private val storagePermissionLauncher = activity.registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        // Handle storage permission result
        if (isGranted) {
            // Permission granted
            // Perform necessary actions
        } else {
            // Permission denied
            // Handle denied permission case
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

@Composable
fun rememberPermissionManager(
    activity: ComponentActivity
): PermissionManager {
    val lifecycleOwner = remember { activity as LifecycleOwner }
    return remember(lifecycleOwner) {
        PermissionManager(activity)
    }
}

