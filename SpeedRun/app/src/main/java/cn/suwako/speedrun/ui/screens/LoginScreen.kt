package cn.suwako.speedrun.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cn.suwako.speedrun.LocalNavController
import cn.suwako.speedrun.R

@Composable
fun LoginScreen() {
    val navController = LocalNavController.current
    RealLoginScreen(navController)
}

@Composable
fun RealLoginScreen(navController: NavController) {
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
                        value = "",
                        onValueChange = { /*TODO*/ },
                        label = { Text(text = "用户名") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(0.8f)
                    )
                    OutlinedTextField(
                        value = "",
                        onValueChange = { /*TODO*/ },
                        label = { Text(text = "密码") },
                        singleLine = true,
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        modifier = Modifier.fillMaxWidth(0.8f)
                    )
                    Button(
                        onClick = { /*TODO*/ },
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