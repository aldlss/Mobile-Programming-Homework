package cn.suwako.speedrun.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cn.suwako.speedrun.LocalNavController
import cn.suwako.speedrun.MainActivity
import cn.suwako.speedrun.R
import cn.suwako.speedrun.ui.components.BackIconButton
import cn.suwako.speedrun.ui.theme.SpeedRunTheme
import cn.suwako.speedrun.ui.viewmodel.AccountOverviewViewModel

@Composable
fun AccountOverview() {
    val navController = LocalNavController.current
    val isLoggedIn = MainActivity.authManager.getIsLoggedIn()

    if (isLoggedIn) {
        LoginAccountOverview()
    } else {
        navController.navigate("login") {
            popUpTo("accountOverview") {
                inclusive = true
            }
        }
    }
}

@Composable
fun LoginAccountOverview(accountOverviewViewModel: AccountOverviewViewModel = viewModel())
{
    val navController = LocalNavController.current
    val content = LocalContext.current as MainActivity

    LaunchedEffect(MainActivity.authManager.getUserId()) {
        accountOverviewViewModel.loadUser(MainActivity.authManager.getUserId())
    }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = { BackIconButton(navController) },
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
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Column(
                    modifier = Modifier,
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    val avatarModifier = Modifier
                        .size(250.dp)
                        .padding(10.dp)
                        .clip(CircleShape)
                    accountOverviewViewModel.avatar?.also { bitmap ->
                        Image(
                            bitmap = bitmap.asImageBitmap(),
                            contentDescription = "Avatar",
                            modifier = avatarModifier,
                            contentScale = ContentScale.Crop,
                        )
                    } ?: Image(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_launcher_background),
                        contentDescription = "Default Avatar",
                        modifier = avatarModifier,
                        contentScale = ContentScale.Fit,
                    )

                    Text(
                        text = accountOverviewViewModel.nickname,
                        style = MaterialTheme.typography.h3,
                    )
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
                    onClick = {
                        MainActivity.authManager.logout()
                        Toast.makeText(content, "已退出账号", Toast.LENGTH_SHORT).show()
                        navController.navigateUp()
                    },
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
        LoginAccountOverview()
    }
}