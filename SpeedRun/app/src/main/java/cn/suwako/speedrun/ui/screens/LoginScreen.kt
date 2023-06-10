package cn.suwako.speedrun.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import cn.suwako.speedrun.LocalNavController
import cn.suwako.speedrun.MainActivity
import cn.suwako.speedrun.ui.components.BackIconButton
import cn.suwako.speedrun.ui.viewmodel.LoginViewModel
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(loginViewModel: LoginViewModel = viewModel()) {
    val navController = LocalNavController.current
    val scope = rememberCoroutineScope()
    val content = LocalContext.current as MainActivity

    LaunchedEffect(key1 = Lifecycle.Event.ON_RESUME) {
        loginViewModel.loginSuccess.collect {
            if (it) {
                Toast.makeText(content, "登录成功", Toast.LENGTH_SHORT).show()
                navController.navigateUp()

            } else {
                Toast.makeText(content, "登录失败", Toast.LENGTH_SHORT).show()
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = { BackIconButton(navController) },
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
                        value = loginViewModel.userId,
                        onValueChange = { newUserId ->
                            loginViewModel.userId = newUserId
                        },
                        label = { Text(text = "用户名") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(0.8f)
                    )
                    OutlinedTextField(
                        value = loginViewModel.password,
                        onValueChange = { newPassword ->
                            loginViewModel.password = newPassword
                        },
                        label = { Text(text = "密码") },
                        singleLine = true,
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        modifier = Modifier.fillMaxWidth(0.8f)
                    )
                    Button(
                        onClick = {
                            scope.launch {
                                loginViewModel.login()
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