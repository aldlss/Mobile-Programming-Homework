package cn.suwako.speedrun.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import cn.suwako.speedrun.R
import cn.suwako.speedrun.ui.theme.SpeedRunTheme

@Composable
fun AppSetting(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "应用设置") },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.navigateUp()
                        },
                        content = {
                            Icon(
                                imageVector = ImageVector.vectorResource(id = R.drawable.materialsymbolsarrowbackrounded),
                                contentDescription = "BackIcon"
                            )
                        }
                    )
                },
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
fun AppSettingPreview() {
    SpeedRunTheme {
        AppSetting(navController = rememberNavController())
    }
}