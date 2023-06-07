package cn.suwako.speedrun.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import cn.suwako.speedrun.MainActivity
import cn.suwako.speedrun.R
import cn.suwako.speedrun.data.local.entities.User
import cn.suwako.speedrun.ui.theme.SpeedRunTheme
import kotlinx.coroutines.launch

@Composable
fun AccountDetail(navController: NavController) {

    val sharedPref = cn.suwako.speedrun.LocalSharedPreferences.current
    val userId = sharedPref.getString(stringResource(R.string.LoggedInUserId), "")!!

    val user = remember { mutableStateOf(User("5", "satori", "0")) }
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(userId) {
        coroutineScope.launch {
            user.value = MainActivity.database.userDao().getUserById(userId)?:user.value
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "个人信息") },
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
                        onClick = {
                            coroutineScope.launch {
                                MainActivity.database.userDao().updateUsers(user.value)
                            }
                            navController.navigateUp()
                        },
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
                    Button(
                        onClick = { /*TODO*/ },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                            .height(60.dp),
                    ) {
                        Text(text = "修改头像")
                    }
                    Text(
                        text = "用户名: ${user.value.id}",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                    )
                    OutlinedTextField(
                        value = user.value.nickname,
                        onValueChange = { newNickname ->
                            user.value = user.value.copy(nickname = newNickname)
                        },
                        label = { Text(text = "昵称") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                    )
                    OutlinedTextField(
                        value = user.value.email?:"",
                        onValueChange = { newEmail ->
                            user.value = user.value.copy(email = newEmail)
                        },
                        label = { Text(text = "邮箱") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                    )
                    OutlinedTextField(
                        value = user.value.phone?:"",
                        onValueChange = { newPhone ->
                            user.value = user.value.copy(phone = newPhone)
                        },
                        label = { Text(text = "手机号") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                    )
                    OutlinedTextField(
                        value = user.value.address?:"",
                        onValueChange = { newAddress ->
                            user.value = user.value.copy(address = newAddress)
                        },
                        label = { Text(text = "地址") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AccountDetailPreview() {
    SpeedRunTheme {
        AccountDetail(navController = rememberNavController())
    }
}