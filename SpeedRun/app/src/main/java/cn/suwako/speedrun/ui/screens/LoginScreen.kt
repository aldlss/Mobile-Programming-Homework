package cn.suwako.speedrun.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import cn.suwako.speedrun.LocalNavController
import cn.suwako.speedrun.LocalSharedPreferences
import cn.suwako.speedrun.MainActivity
import cn.suwako.speedrun.R
import kotlinx.coroutines.launch

@Composable
fun LoginScreen() {
    val navController = LocalNavController.current
    RealLoginScreen(navController)
}

@Composable
fun RealLoginScreen(navController: NavController) {

    val coroutineScope = rememberCoroutineScope()
    val username = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    val sharedPref = LocalSharedPreferences.current

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = { IconButton(
                    onClick = { navController.navigateUp() },
                    content = { Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.materialsymbolsarrowbackrounded),
                        contentDescription = "BackIcon") }
                ) },
                title = { Text(text = "登录") },
                actions = {
                    Button(
                        onClick = {
                            navController.navigate("register") {
                                popUpTo("login") {
                                    inclusive = true
                                }
                            }
                        },
                    ) {
                        Text(text = "注册")
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
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                // Input
                Column(
                    modifier = Modifier,
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    OutlinedTextField(
                        value = username.value,
                        onValueChange = { newUsername ->
                            username.value = newUsername
                        },
                        label = { Text(text = "用户名") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(0.8f)
                    )
                    OutlinedTextField(
                        value = password.value,
                        onValueChange = { newPassword ->
                            password.value = newPassword
                        },
                        label = { Text(text = "密码") },
                        singleLine = true,
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        modifier = Modifier.fillMaxWidth(0.8f)
                    )
                    val isLoggedInKey = stringResource(R.string.IsLoggedIn)
                    val loggedInUserIdKey = stringResource(R.string.LoggedInUserId)
                    Button(
                        onClick = {
                            coroutineScope.launch {
                                val user = MainActivity.database.userDao().getUserById(username.value)
                                if (user == null || user.password != password.value) {
                                    return@launch
                                }
                                with(sharedPref.edit()) {
                                    putBoolean(isLoggedInKey, true)
                                    putString(loggedInUserIdKey, username.value)
                                    apply()
                                }
                                navController.navigateUp()
                            }
                        },
                        modifier = Modifier.fillMaxWidth(0.5f)
                    ) {
                        Text(text = "登录")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen()
}