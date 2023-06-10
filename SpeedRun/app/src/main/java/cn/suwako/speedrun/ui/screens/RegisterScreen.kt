package cn.suwako.speedrun.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import cn.suwako.speedrun.ui.viewmodel.RegisterViewModel

@Composable
fun RegisterScreen(registerViewModel: RegisterViewModel = viewModel()) {

    val navController = LocalNavController.current
    val content = LocalContext.current as MainActivity

    LaunchedEffect(key1 = Lifecycle.Event.ON_RESUME) {
        registerViewModel.registerSuccess.collect {
            if (it) {
                Toast.makeText(content, "注册成功", Toast.LENGTH_SHORT).show()
                navController.navigate("login") {
                    popUpTo("register") {
                        inclusive = true
                    }
                }
            } else {
                Toast.makeText(content, "注册失败", Toast.LENGTH_SHORT).show()
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = { BackIconButton(navController) },
                title = { Text(text = "注册") },
                actions = {
                    Button(
                        onClick = {
                            navController.navigate("login") {
                                popUpTo("register") {
                                    inclusive = true
                                }
                            }
                        },
                    ) {
                        Text(text = "登录")
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
                        value = registerViewModel.username,
                        onValueChange = { newUsername ->
                            registerViewModel.username = newUsername
                        },
                        label = { Text(text = "用户名") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(0.8f)
                    )
                    OutlinedTextField(
                        value = registerViewModel.password,
                        onValueChange = { newPassword ->
                            registerViewModel.password = newPassword
                        },
                        label = { Text(text = "密码") },
                        singleLine = true,
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        modifier = Modifier.fillMaxWidth(0.8f)
                    )
                    OutlinedTextField(
                        value = registerViewModel.confirmPassword,
                        onValueChange = { newConfirmPassword ->
                            registerViewModel.confirmPassword = newConfirmPassword
                        },
                        label = { Text(text = "重复密码") },
                        singleLine = true,
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        modifier = Modifier.fillMaxWidth(0.8f)
                    )
                    Button(
                        onClick = {
                            registerViewModel.register()
                        },
                        modifier = Modifier.fillMaxWidth(0.5f)
                    ) {
                        Text(text = "注册")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RegisterScreenPreview() {
    LoginScreen()
}