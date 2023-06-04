package cn.suwako.speedrun.ui.components

import android.widget.TimePicker
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import cn.suwako.speedrun.LocalNavController
import cn.suwako.speedrun.R
import cn.suwako.speedrun.ui.theme.SpeedRunTheme

@Composable
fun RunView() {
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
            Text("现场场景记录")
            Button(
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .fillMaxHeight(0.16f),
                onClick = { /*TODO*/ },
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxSize(),
                    painter = painterResource(id = R.drawable.materialsymbolsandroidcamera),
                    contentDescription = "CameraIcon",
                    contentScale = ContentScale.Fit,
                )
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
                    text = "00:00:00",
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text("已跑距离：")
                Text(
                    text = "0.00 KM",
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text("平均速度：")
                Text(
                    text = "0.00 KM/H",
                )
            }
            Text("设置时间占坑")
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

                onClick = { /*TODO*/ },
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

                onClick = { /*TODO*/ },
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