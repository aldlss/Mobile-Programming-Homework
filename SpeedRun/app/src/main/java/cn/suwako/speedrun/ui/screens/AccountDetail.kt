package cn.suwako.speedrun.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import cn.suwako.speedrun.LocalNavController
import cn.suwako.speedrun.MainActivity
import cn.suwako.speedrun.R
import cn.suwako.speedrun.ui.components.BackIconButton
import cn.suwako.speedrun.ui.components.RequestPicture
import cn.suwako.speedrun.ui.theme.SpeedRunTheme
import cn.suwako.speedrun.ui.viewmodel.AccountDetailViewModel

@Composable
fun AccountDetail(accountDetailViewModel: AccountDetailViewModel = viewModel()) {

    val navController = LocalNavController.current
    val content = LocalContext.current as MainActivity

    LaunchedEffect(key1 = Lifecycle.Event.ON_RESUME) {
        accountDetailViewModel.saveSuccess.collect {
            if (it) {
                Toast.makeText(content, "保存成功", Toast.LENGTH_SHORT).show()
            }
        }
    }

    LaunchedEffect(MainActivity.authManager.getUserId()) {
        accountDetailViewModel.loadUser(MainActivity.authManager.getUserId())
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "个人信息") },
                navigationIcon = { BackIconButton(navController) },
                actions = {
                    Button(
                        onClick = {
                            accountDetailViewModel.updateUser(content.filesDir)
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
            var showRequestPicture by remember { mutableStateOf(false) }
            if (showRequestPicture) {
                RequestPicture(
                    onCancel = { showRequestPicture = false },
                    onImageSelected = { bitmapNullable ->
                        bitmapNullable?.let { bitmap ->
                            accountDetailViewModel.avatar = bitmap
                            Toast.makeText(content, "头像已选择，保存后生效", Toast.LENGTH_SHORT).show()
                        }
                        showRequestPicture = false
                    }
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                val avatarModifier = Modifier
                    .size(200.dp)
                    .padding(10.dp)
                    .clip(CircleShape)

                accountDetailViewModel.avatar?.also { bitmap ->
                    Image(
                        bitmap = bitmap.asImageBitmap(),
                        contentDescription = "avatar",
                        modifier = avatarModifier,
                        contentScale = ContentScale.Crop,
                    )
                } ?: Image(
                    painter = painterResource(id = R.drawable.ic_launcher_background),
                    contentDescription = "default avatar",
                    modifier = avatarModifier,
                    contentScale = ContentScale.Fit,
                )
                Column(
                    modifier = Modifier,
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Button(
                        onClick = { showRequestPicture = true },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                            .height(60.dp),
                    ) {
                        Text(text = "修改头像")
                    }
                    val settingModifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                    Text(
                        text = "用户名: ${accountDetailViewModel.user.id}",
                        modifier = settingModifier
                    )
                    OutlinedTextField(
                        value = accountDetailViewModel.user.nickname,
                        onValueChange = { newNickname ->
                            accountDetailViewModel.user = accountDetailViewModel.user.copy(nickname = newNickname)
                        },
                        label = { Text(text = "昵称") },
                        modifier = settingModifier
                    )
                    OutlinedTextField(
                        value = accountDetailViewModel.user.email ?: "",
                        onValueChange = { newEmail ->
                            accountDetailViewModel.user = accountDetailViewModel.user.copy(email = newEmail)
                        },
                        label = { Text(text = "邮箱") },
                        modifier = settingModifier
                    )
                    OutlinedTextField(
                        value = accountDetailViewModel.user.phone ?: "",
                        onValueChange = { newPhone ->
                            accountDetailViewModel.user = accountDetailViewModel.user.copy(phone = newPhone)
                        },
                        label = { Text(text = "手机号") },
                        modifier = settingModifier
                    )
                    OutlinedTextField(
                        value = accountDetailViewModel.user.address ?: "",
                        onValueChange = { newAddress ->
                            accountDetailViewModel.user = accountDetailViewModel.user.copy(address = newAddress)
                        },
                        label = { Text(text = "地址") },
                        modifier = settingModifier
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
        CompositionLocalProvider(LocalNavController provides rememberNavController()) {
            CompositionLocalProvider(LocalContext provides MainActivity()) {
                AccountDetail()
            }
        }
    }
}