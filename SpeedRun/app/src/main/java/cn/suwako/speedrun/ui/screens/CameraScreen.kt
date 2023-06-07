package cn.suwako.speedrun.ui.screens

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Picture
import androidx.activity.ComponentActivity
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import cn.suwako.speedrun.LocalNavController
import cn.suwako.speedrun.LocalPermissionManager
import cn.suwako.speedrun.ui.utils.PermissionManager
import cn.suwako.speedrun.ui.viewmodel.CameraViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

var CapturePicture by mutableStateOf<Bitmap?>(null)

@Composable
fun CameraPreview() {
    val lifecycleOwner = LocalLifecycleOwner.current

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            val previewView = PreviewView(context)

            val cameraProviderFuture = ProcessCameraProvider.getInstance(context)

            cameraProviderFuture.addListener({
                val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

                val preview = Preview.Builder()
                    .build()
                    .also {
                        it.setSurfaceProvider(previewView.surfaceProvider)
                    }

                val imageCapture = ImageCapture.Builder()
                    .build()

                val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

                try {
                    cameraProvider.unbindAll()

                    cameraProvider.bindToLifecycle(
                        lifecycleOwner,
                        cameraSelector,
                        preview,
                        imageCapture
                    )

                } catch (exc: Exception) {
                    // 处理相机启动错误
                }

            }, ContextCompat.getMainExecutor(context))

            previewView
        }
    )
}

@Composable
fun CameraScreen() {
    val navController = LocalNavController.current
    val permissionManager = LocalPermissionManager.current
    if (permissionManager.checkCameraPermission()) {
        CameraPreview()
        CaptureButton()
    } else {
        permissionManager.requestCameraPermission()
        navController.navigateUp()
    }
}
@androidx.compose.ui.tooling.preview.Preview(showBackground = true, showSystemUi = true)
@Composable
fun CameraScreenPreview() {
//    CameraScreen()
}

@Composable
fun CaptureButton() {

    val context = LocalContext.current
    val navigator = LocalNavController.current
    val coroutineScope = rememberCoroutineScope()

    val imageCapture = ImageCapture.Builder().build()

    // 拍照方法
    suspend fun takePhoto() {
        withContext(Dispatchers.IO) {
            try {

                // 创建保存路径
                val outputDirectory = File(context.filesDir, "photos")
                outputDirectory.mkdirs()

                // 创建照片文件
                val photoFile = File(outputDirectory, generateFileName())

                // 拍照
                val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
                imageCapture.takePicture(
                    outputOptions,
                    ContextCompat.getMainExecutor(context),
                    object : ImageCapture.OnImageSavedCallback {
                        override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                            // 照片保存成功后的处理
                            // 您可以在这里处理保存后的照片文件
                            // read image
                            CapturePicture = BitmapFactory.decodeFile(photoFile.absolutePath)
                            navigator.navigateUp()
                        }

                        override fun onError(exception: ImageCaptureException) {
                            // 拍照错误的处理
                        }
                    })
            } catch (exception: Exception) {
                throw exception
            }
        }
    }

    Button(onClick = {
        coroutineScope.launch {
            takePhoto()
        }
    }) {
        Text(text = "拍照")
    }

    // 调用拍照方法
    // 您可以将该方法绑定到拍照按钮的点击事件中
    // 每次点击按钮时将调用该方法拍照
    // 拍照完成后，您可以在 onImageSaved 回调方法中处理保存的照片文件
    // onError 方法用于处理拍照过程中的错误
    // 您可以根据需要添加适当的错误处理逻辑
    // 由于该方法是挂起函数，因此需要在协程作用域中调用
    // 您可以在 Composable 函数的内部调用该方法
}

// 生成照片文件名
private fun generateFileName(): String {
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
    return "IMG_$timeStamp.jpg"
}
