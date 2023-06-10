package cn.suwako.speedrun.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import cn.suwako.speedrun.LocalNavController
import cn.suwako.speedrun.ui.components.BackIconButton
import cn.suwako.speedrun.R
import cn.suwako.speedrun.ui.theme.SpeedRunTheme

@Composable
fun AboutScreen() {

    val navController = LocalNavController.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "关于") },
                navigationIcon = { BackIconButton(navController) },
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
        ) {
            Column(
                Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    text = "简单运动记录软件",
                    style = MaterialTheme.typography.h4,
                )

                var click by remember { mutableStateOf(true) }

                Image(
                    modifier = Modifier
                        .fillMaxSize(0.5f)
                        .clickable { click = !click },
                    painter = painterResource(id = if(click) R.drawable.a7 else R.drawable._109951164079708306),
                    contentDescription = "me",
                    contentScale = ContentScale.Fit
                )

                Text(
                    text = "aldlss\nsuwako.cn",
                    style = MaterialTheme.typography.h5,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun AboutScreenPreview() {
    CompositionLocalProvider(LocalNavController provides rememberNavController()){
        SpeedRunTheme {
            AboutScreen()
        }
    }
}