package cn.suwako.speedrun.ui.viewmodel

import android.graphics.Bitmap
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class CameraViewModel: ViewModel() {
    var CapturePicture by mutableStateOf<Bitmap?>(null)
}