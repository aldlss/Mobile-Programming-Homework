package cn.suwako.speedrun.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun NoLoginView(navController: NavController)
{
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "登录以使用",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )
        Button(
            onClick = {
                navController.navigate("login")
            }
        ) {
            Text(
                text = "登录",
                fontSize = 24.sp,
            )
        }
    }
}