package cn.suwako.speedrun.ui.viewmodel

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cn.suwako.speedrun.MainActivity
import cn.suwako.speedrun.data.local.entities.RunData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.time.LocalDate

class DataDetailViewModel: ViewModel() {
    private var runData: RunData? = null
    var capturePicture by mutableStateOf<Bitmap?>(null)
    var runDetails = mutableStateListOf<Pair<String, String>>()

    private val _deleteSuccess: Channel<Boolean> = Channel()
    val deleteSuccess = _deleteSuccess.receiveAsFlow()

    fun requestRunData(runDataId: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                runData = MainActivity.database.runDataDao().getRunDataById(runDataId)
            }

            runData?.let {
                capturePicture = it.runPicture?.let { picPath ->
                    withContext(Dispatchers.IO) {
                        BitmapFactory.decodeFile(picPath)
                    }
                }

                withContext(Dispatchers.Default) {
                    // 不清除旋转之后会在套一次 = =
                    runDetails.clear()
                    runDetails.addAll(
                        listOf(
                            Pair("跑步时间", it.runTime.toString()),
                            Pair("跑步距离", it.runDistance.toString()),
                            Pair("跑步速度", "%.2f".format(it.runSpeed)),
                            Pair("跑步步数", it.runSteps.toString()),
                            Pair("消耗热量", "%.2f".format(it.runCalories)),
                            Pair("跑步地点", it.runLocation),
                        )
                    )
                }

            }

        }
    }

    fun deleteRunData() {
        viewModelScope.launch {
            runData?.also {
                withContext(Dispatchers.IO) {
                    it.runPicture?.let { picPath ->
                        File(picPath).delete()
                    }
                    MainActivity.database.runDataDao().delete(it)
                }
                _deleteSuccess.send(true)
            } ?: _deleteSuccess.send(false)
        }
    }
}