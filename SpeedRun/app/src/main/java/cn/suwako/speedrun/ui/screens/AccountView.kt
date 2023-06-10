package cn.suwako.speedrun.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cn.suwako.speedrun.LocalNavController

@Composable
fun AccountView() {
    val navController = LocalNavController.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val modifierFill = Modifier.fillMaxWidth().fillMaxHeight(0.08f)
        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Button(
                modifier = modifierFill,
                onClick = {
                    navController.navigate("accountOverview")
                }
            ) {
                Text(text = "账户")
            }
        }
        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Button(
                modifier = modifierFill,
                onClick = {
                    navController.navigate("appSetting")
                }
            ) {
                Text(text = "设置")
            }
            Button(
                modifier = modifierFill,
                onClick = {
                    navController.navigate("about")
                }
            ) {
                Text(text = "关于")
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AccountViewPreview(){
    AccountView()
}