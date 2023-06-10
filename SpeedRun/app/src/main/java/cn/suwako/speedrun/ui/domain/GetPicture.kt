package cn.suwako.speedrun.ui.domain

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

class GetPicture(private val activity: ComponentActivity) {

    private val _bitmapPic: Channel<Bitmap?> = Channel()
    val bitmapPic = _bitmapPic.receiveAsFlow()

    private val getCameraPic = activity.registerForActivityResult(
        ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        _bitmapPic.trySend(bitmap)
    }

    private val getGalleryPic = activity.registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri -> // 这样可以，之前在这里加了个 suspend 直接没反应了
        // load to bitmap by uri
        if (uri == null) {
            _bitmapPic.trySend(null)
        } else {
            val bitmap = let {
                activity.contentResolver.openInputStream(uri)?.use {
                    BitmapFactory.decodeStream(it)
                }
            }
            _bitmapPic.trySend(bitmap)
        }
    }

    fun getCameraPicture() {
        getCameraPic.launch(null)
    }

    fun getGalleryPicture() {
        getGalleryPic.launch("image/*")
    }
}