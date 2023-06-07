package cn.suwako.speedrun.ui.viewmodel

import android.os.CountDownTimer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.room.Dao
import cn.suwako.speedrun.data.local.entities.RunData
import java.text.SimpleDateFormat
import java.util.*
import kotlin.time.times

class RunViewModel: ViewModel() {
    var pickMinute by mutableStateOf(0)

    // s
    var useTime by mutableStateOf(0)

    // m
    var runDistance by mutableStateOf(0)

    // m/s
    var runSpeed by mutableStateOf(0)

    val randomDistance = (4..12)

    private lateinit var countDownTimer: CountDownTimer
    fun StartRun() {
        runDistance = 0
        val sumTime = (pickMinute * 60 * 1000).toLong()
        countDownTimer = object : CountDownTimer(sumTime , 1000) {
            override fun onTick(millisUntilFinished: Long) {
                useTime = (sumTime - millisUntilFinished).toInt() / 1000
                runDistance += randomDistance.random()
                if(useTime > 0)
                    runSpeed = runDistance / useTime
            }

            override fun onFinish() {
//                println("Countdown finished!")
            }
        }.start()
    }

    fun StopRun() {
        countDownTimer.cancel()

        RunData(
            userId = "1",
            runTime = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(useTime),
            runDate = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(Date()),
            runLocation = "Shanghai",
            runDistance = runDistance.toString(),
            runPace = runSpeed.toString(),
            runCalories = "100",
            runSteps = (runDistance * 2).toString(),
        )


    }
}