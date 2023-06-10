package cn.suwako.speedrun.ui.components

import android.graphics.Bitmap
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import cn.suwako.speedrun.LocalPermissionManager
import cn.suwako.speedrun.MainActivity
import cn.suwako.speedrun.ui.theme.SpeedRunTheme
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch

@Composable
fun RequestPicture(onCancel: () -> Unit, onImageSelected: (Bitmap?) -> Unit) {
    val context = LocalContext.current
    val permissionManager = LocalPermissionManager.current
    val scope = rememberCoroutineScope()

    Dialog(
        onDismissRequest = onCancel,
    ) {
        Surface(
            modifier = Modifier
                .width(200.dp)
                .height(100.dp),
            shape = MaterialTheme.shapes.medium,
        ) {
            Column(
                modifier = Modifier,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                val buttonModifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                Button(
                    modifier = buttonModifier,
                    onClick = {
                        scope.launch {
                            // 期望能够把两个 if 的逻辑合并，但是之前的实现 collect 这边好像会有下面的代码没执行到的问题
                            if (permissionManager.checkCameraPermission()) {
                                requestPictureByCamera(onImageSelected)
                            } else {
                                Toast.makeText(context, "请先授予相机权限", Toast.LENGTH_SHORT).show()
                                permissionManager.requestCameraPermission()
                                permissionManager.getPermissionSuccess.take(1).collectLatest {
                                    if(it) {
                                        requestPictureByCamera(onImageSelected)
                                    }
                                }
                            }
                        }
                    }
                ) {
                    Text(text = "拍照")
                }
                Button(
                    modifier = buttonModifier,
                    onClick = {
                        scope.launch {
                            if (permissionManager.checkStoragePermission()) {
                                requestPictureByGallery(onImageSelected)
                            } else {
                                Toast.makeText(context, "请先授予读取储存权限", Toast.LENGTH_SHORT).show()
                                permissionManager.requestStoragePermission()
                                permissionManager.getPermissionSuccess.collectLatest {
                                    if(it) {
                                        requestPictureByGallery(onImageSelected)
                                    }
                                }
                            }
                        }
                    }
                ) {
                    Text(text = "从相册选择")
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun RequestPicturePreview(){
    SpeedRunTheme {
        RequestPicture({}, {})
    }
}

private suspend fun requestPictureByCamera(onCaptured: (Bitmap?) -> Unit) {
    MainActivity.getPictureManager.getCameraPicture()
    MainActivity.getPictureManager.bitmapPic.take(1).collectLatest {
        onCaptured(it)
    }
}

private suspend fun requestPictureByGallery(onCaptured: (Bitmap?) -> Unit) {
    MainActivity.getPictureManager.getGalleryPicture()
    MainActivity.getPictureManager.bitmapPic.take(1).collectLatest {
        onCaptured(it)
    }
}
