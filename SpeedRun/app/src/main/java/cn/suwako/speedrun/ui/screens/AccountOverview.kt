package cn.suwako.speedrun.ui.screens

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cn.suwako.speedrun.LocalNavController
import cn.suwako.speedrun.R
import cn.suwako.speedrun.ui.theme.SpeedRunTheme

@Composable
fun AccountOverview() {
    val navController = LocalNavController.current
    val sharedPref = cn.suwako.speedrun.LocalSharedPreferences.current
    val isLoggedin = remember { sharedPref.getBoolean("isLoggedin", false) }
    if (isLoggedin) {
        val navControllerInner = rememberNavController()
        NavHost(navControllerInner, startDestination = "loggedinAccountOverview") {
            composable("loggedinAccountOverview") {
                LoggedinAccountOverview()
            }
            composable("accountDetail") {
                AccountDetail(navControllerInner)
            }
            composable("accountSetting") {
                AccountSetting(navControllerInner)
            }
//            composable("changePassword") {
//                AccountPassword()
//            }
        }
    } else {
        navController.navigate("login") {
            popUpTo("accountOverview") {
                inclusive = true
            }
        }
    }
}

@Composable
fun LoggedinAccountOverview()
{
    val navController = LocalNavController.current

    Scaffold(
        topBar = {
            TopAppBar(
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
                title = { Text(text = "账户") },
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Column(
                    modifier = Modifier,
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Image(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_launcher_background),
                        contentDescription = "Avatar",
                        modifier = Modifier
                            .size(100.dp)
                            .padding(10.dp)
                            .clip(CircleShape)
                    )
                    Text(text = "账号：xxxxxxxx")
                }
                val modifierFillW = Modifier
                    .fillMaxWidth(0.8f)
                Column(
                    modifier = Modifier,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Button(
                        modifier = modifierFillW,
                        onClick = {
                            navController.navigate("accountDetail")
                        },
                    ) {
                        Text(text = "个人信息")
                    }
                    Button(
                        modifier = modifierFillW,
                        onClick = {
                            navController.navigate("accountSetting")
                        },
                    ) {
                        Text(text = "设置")
                    }
//                    Button(
//                        modifier = modifierFillW,
//                        onClick = { /*TODO*/ },
//                    ) {
//                        Text(text = "修改密码")
//                    }
                }
                Button(
                    modifier = modifierFillW,
                    onClick = { /*TODO*/ },
                ) {
                    Text(text = "退出登录")
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AccountOverviewPreview() {
    SpeedRunTheme {
        LoggedinAccountOverview()
    }
}