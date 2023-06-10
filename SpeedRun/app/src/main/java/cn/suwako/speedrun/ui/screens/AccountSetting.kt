package cn.suwako.speedrun.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import cn.suwako.speedrun.LocalNavController
import cn.suwako.speedrun.ui.components.BackIconButton
import cn.suwako.speedrun.ui.theme.SpeedRunTheme

@Composable
fun AccountSetting() {

    val navController = LocalNavController.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "个人设置") },
                navigationIcon = { BackIconButton(navController) },
                actions = {
                    Button(
                        onClick = { /*TODO*/ },
                    ) {
                        Text(text = "保存")
                    }
                }
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
        ) {

        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AccountSettingPreview() {
    SpeedRunTheme {
        AccountSetting()
    }
}