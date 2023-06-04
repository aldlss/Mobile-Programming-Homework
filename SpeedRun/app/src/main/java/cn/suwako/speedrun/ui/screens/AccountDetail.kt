package cn.suwako.speedrun.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import cn.suwako.speedrun.R
import cn.suwako.speedrun.ui.theme.SpeedRunTheme

@Composable
fun AccountDetail(navController: NavController) {
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
                    OutlinedTextField(
                        value = "昵称",
                        onValueChange = { /*TODO*/ },
                        label = { Text(text = "昵称") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                    )
                    OutlinedTextField(
                        value = "邮箱",
                        onValueChange = { /*TODO*/ },
                        label = { Text(text = "邮箱") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                    )
                    OutlinedTextField(
                        value = "手机号",
                        onValueChange = { /*TODO*/ },
                        label = { Text(text = "手机号") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                    )
                    OutlinedTextField(
                        value = "地址",
                        onValueChange = { /*TODO*/ },
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