package cn.suwako.speedrun.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import cn.suwako.speedrun.LocalNavController
import cn.suwako.speedrun.MainActivity
import cn.suwako.speedrun.R
import cn.suwako.speedrun.ui.components.*
import cn.suwako.speedrun.ui.theme.SpeedRunTheme
import cn.suwako.speedrun.ui.viewmodel.RunViewModel
import cn.suwako.speedrun.ui.viewmodel.RunViewModel.RunState.*

@Composable
fun RunViewEntry() {
    val navController = LocalNavController.current
    val isLoggedIn = MainActivity.authManager.getIsLoggedIn()

    if (isLoggedIn) {
        RunView()
    } else {
        NoLoginView(navController)
    }
}

@Composable
fun RunView(runViewModel: RunViewModel = viewModel()) {

    var selectPicShow by remember { mutableStateOf(false) }
    if (selectPicShow) {
        RequestPicture(
            onCancel = {
                selectPicShow = false
            }
        ) {
            it?.let { bitmap ->
                runViewModel.capturePicture = bitmap
            }
            selectPicShow = false
        }
    }

    var pickTimeShow by remember { mutableStateOf(false) }
    if (pickTimeShow) {
        TimePicker(
            onCancel = {
                pickTimeShow = false
            }
        ) {
            runViewModel.pickTime = it
            pickTimeShow = false
        }
    }

    if(runViewModel.isTimeUp) {
        TimeUpAlert {
            runViewModel.isTimeUp = false
        }
    }

    // 这两个框比较重要，而且是个无状态的提示框，用 rememberSaveable 可以一点
    var stopEnsureDialogShow by rememberSaveable { mutableStateOf(false) }
    var saveRunDataEnsureDialogShow by rememberSaveable { mutableStateOf(false) }

    if (stopEnsureDialogShow) {
        EnsureAlertDialog(
            text = "确定要结束本次跑步吗？",
            onConfirm = {
                runViewModel.stopRun()
                stopEnsureDialogShow = false
                saveRunDataEnsureDialogShow = true
            },
            onCancel = {
                stopEnsureDialogShow = false
            }
        )
    }

    if (saveRunDataEnsureDialogShow) {
        val context = LocalContext.current
        EnsureAlertDialog(
            text = "是否保存本次跑步记录",
            onConfirm = {
                runViewModel.saveRunData(context.filesDir)
                saveRunDataEnsureDialogShow = false
            },
            onCancel = {
                saveRunDataEnsureDialogShow = false
            },
            onDismiss = {}
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column(
            modifier = Modifier
                .padding(0.dp, 10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                Text("现场场景记录")
                val imageModifier = Modifier
                    .fillMaxWidth()
                    .size(150.dp)
                    .clickable(
                        enabled = runViewModel.runState != STOP,
                    ) {
                        selectPicShow = true
                    }

                // 我其实不喜欢这个写法，因为它判断了两次 null，还不如直接 if
                // 但是由于这样写 IDE 不会让我加 !!，而且看起来高级点，于是就这样写了
                // 突然想念 C++ 的 if 内赋值判断了
                runViewModel.capturePicture?.also { bitmap ->
                    Image(
                        modifier = imageModifier,
                        bitmap = bitmap.asImageBitmap(),
                        contentDescription = "capture picture",
                        contentScale = ContentScale.Fit,
                    )
                } ?: run {
                    Image(
                        modifier = imageModifier,
                        painter = painterResource(id = R.drawable.materialsymbolsandroidcamera),
                        contentDescription = "CameraIcon",
                        contentScale = ContentScale.Fit,
                    )
                }
            }
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(1.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text("用时：${runViewModel.useTime}")
            Text("已跑距离：${runViewModel.runDistance} 米")
            Text("平均速度：%.1f 米/秒".format(runViewModel.runSpeed))
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    enabled = runViewModel.runState == STOP,
                    onClick = {
                        pickTimeShow = true
                    }
                ),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                fontWeight = FontWeight.Bold,
                fontSize = MaterialTheme.typography.h3.fontSize,
                text = "${runViewModel.pickTime.hour} 时 ${runViewModel.pickTime.minute} 分 ${runViewModel.pickTime.second} 秒",
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 20.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            when(runViewModel.runState) {
                RUNNING -> {
                    ChangeRunStateButton(
                        onClick = { runViewModel.pauseRun() },
                        painter = painterResource(id = R.drawable.materialsymbolspauserounded),
                        contentDescription = "PauseIcon",
                    )
                    ChangeRunStateButton(
                        onClick = { stopEnsureDialogShow = true },
                        painter = painterResource(id = R.drawable.materialsymbolsstoprounded),
                        contentDescription = "StopIcon",
                    )
                }
                STOP -> {
                    ChangeRunStateButton(
                        onClick = { runViewModel.startRun() },
                        painter = painterResource(id = R.drawable.materialsymbolsplayarrowrounded),
                        contentDescription = "PlayIcon",
                    )
                }
                PAUSE -> {
                    ChangeRunStateButton(
                        onClick = { runViewModel.resumeRun() },
                        painter = painterResource(id = R.drawable.materialsymbolsresumerounded),
                        contentDescription = "ResumeIcon",
                    )
                    ChangeRunStateButton(
                        onClick = { stopEnsureDialogShow = true },
                        painter = painterResource(id = R.drawable.materialsymbolsstoprounded),
                        contentDescription = "StopIcon",
                    )
                }
            }
        }
    }
}

@Composable
fun ChangeRunStateButton(onClick: () -> Unit, painter: Painter, contentDescription: String) {
    Button(
        modifier = Modifier
            .size(80.dp)
            .clip(CircleShape),
        onClick = onClick,
    ) {
        Image(
            modifier = Modifier
                .fillMaxSize(),
            painter = painter,
            contentDescription = contentDescription,
            contentScale = ContentScale.Fit,
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RunViewPreview() {
    CompositionLocalProvider(LocalNavController provides rememberNavController()) {
        SpeedRunTheme {
            RunView()
        }
    }
}