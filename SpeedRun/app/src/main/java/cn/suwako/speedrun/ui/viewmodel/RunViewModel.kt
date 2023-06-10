package cn.suwako.speedrun.ui.viewmodel

import android.graphics.Bitmap
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cn.suwako.speedrun.MainActivity
import cn.suwako.speedrun.data.local.entities.RunData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID

class RunViewModel: ViewModel() {
    var capturePicture by mutableStateOf<Bitmap?>(null)

    var pickTime: LocalTime by mutableStateOf(LocalTime.of(0, 0, 0))

    // s
    var useTime: LocalTime by mutableStateOf(LocalTime.of(0, 0, 0))

    // m
    var runDistance by mutableStateOf(0)

    // m/s
    var runSpeed by mutableStateOf(0f)

    var isTimeUp by mutableStateOf(false)

    enum class RunState {
        RUNNING, STOP, PAUSE
    }

    var runState by mutableStateOf(RunState.STOP)

    private val randomDistance = (4..12)

    fun startRun() {
        viewModelScope.launch {
            runDistance = 0
            useTime = LocalTime.of(0, 0, 0)
            capturePicture = null
            runSpeed = 0f
            runState = RunState.RUNNING
            launch {
                running()
            }
        }
    }

    private suspend fun running() {
        delay(1000)
        while (runState == RunState.RUNNING) {
            if (pickTime.toSecondOfDay() != 0) {
                pickTime = pickTime.minusSeconds(1)
                if (pickTime.toSecondOfDay() == 0) {
                    isTimeUp = true
                }
            }
            useTime = useTime.plusSeconds(1)
            runDistance += randomDistance.random()
            runSpeed = runDistance.toFloat() / useTime.toSecondOfDay()
            delay(1000)
        }
    }

    fun stopRun() {
        runState = RunState.STOP
    }

    fun saveRunData(fileDir: File) {
        viewModelScope.launch {
            val userId = MainActivity.authManager.getUserId()
            val runPictureDir = File(fileDir, "runPicture")
            if (!runPictureDir.exists()) {
                runPictureDir.mkdir()
            }

            val runPicturePath = capturePicture?.let {
                val file = File(runPictureDir, "${UUID.randomUUID()}.png")
                withContext(Dispatchers.IO) {
                    file.outputStream().use { outputStream ->
                        it.compress(Bitmap.CompressFormat.PNG, 80, outputStream)
                    }
                }
                file.absolutePath
            }
            val runData = RunData(
                userId = userId,
                runTime = useTime,
                runDate = LocalDate.now(),
                runLocation = "Shanghai",
                runDistance = runDistance,
                runSpeed = runSpeed,
                runCalories = 100f,
                runSteps = (runDistance * 1.2).toInt(),
                runPicture = runPicturePath
            )
            withContext(Dispatchers.IO) {
                MainActivity.database.runDataDao().insertAll(runData)
            }
        }
    }

    fun pauseRun() {
        runState = RunState.PAUSE
    }

    fun resumeRun() {
        runState = RunState.RUNNING
        viewModelScope.launch {
            running()
        }
    }
}