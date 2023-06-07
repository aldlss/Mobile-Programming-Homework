package cn.suwako.speedrun.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cn.suwako.speedrun.LocalNavController
import cn.suwako.speedrun.LocalSharedPreferences
import cn.suwako.speedrun.R
import cn.suwako.speedrun.ui.screens.CapturePicture
import cn.suwako.speedrun.ui.theme.SpeedRunTheme
import cn.suwako.speedrun.ui.viewmodel.RunViewModel

@Composable
fun RunView(runViewModel: RunViewModel = viewModel()){
    var capturePicture = remember { CapturePicture }
    val sharedPref = LocalSharedPreferences.current

    Column(
        modifier = Modifier
            .fillMaxSize(),
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
                val navController = LocalNavController.current
                Text("现场场景记录")
                val imageModifier = Modifier
                    .fillMaxWidth(0.85f)
                    .fillMaxHeight(0.16f)
                    .clickable {
                        navController.navigate("camera")
                    }
                if (capturePicture == null) {
                    Image(
                        modifier = imageModifier,
                        painter = painterResource(id = R.drawable.materialsymbolsandroidcamera),
                        contentDescription = "CameraIcon",
                        contentScale = ContentScale.Fit,
                    )
                } else {
                    Image(
                        modifier = Modifier
                            .fillMaxWidth(0.85f)
                            .fillMaxHeight(0.16f)
                            .clickable {
                                navController.navigate("camera")
                            },
                        bitmap = capturePicture.asImageBitmap(),
                        contentDescription = "capture picture",
                        contentScale = ContentScale.Fit,
                    )
                }
            }
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(1.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text("用时：")
                Text(
                    text = "${runViewModel.useTime} 秒",
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text("已跑距离：")
                Text(
                    text = "${runViewModel.runDistance} 米",
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text("平均速度：")
                Text(
                    text = "${runViewModel.runSpeed} 米/秒",
                )
            }
            Text("定时")
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                OutlinedTextField(
                    value = runViewModel.pickMinute.toString(),
                    onValueChange = { runViewModel.pickMinute = it.toInt() },
                    label = { Text("分钟") },
                    modifier = Modifier
                        .fillMaxWidth(0.4f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 20.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Button(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape),

                onClick = {
                    runViewModel.StartRun()
                },
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxSize(),
                    painter = painterResource(id = R.drawable.materialsymbolsplaycirclerounded),
                    contentDescription = "PlayIcon",
                    contentScale = ContentScale.Fit,
                )
            }
            Button(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape),

                onClick = {
                    runViewModel.StopRun(
                    )
                },
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxSize(),
                    painter = painterResource(id = R.drawable.materialsymbolsstopcirclerounded),
                    contentDescription = "StopIcon",
                    contentScale = ContentScale.Fit,
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RunViewPreview() {
    SpeedRunTheme {
        RunView()
    }
}