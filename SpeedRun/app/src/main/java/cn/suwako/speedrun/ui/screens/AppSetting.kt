package cn.suwako.speedrun.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import cn.suwako.speedrun.LocalNavController
import cn.suwako.speedrun.LocalSharePreferences
import cn.suwako.speedrun.ui.components.BackIconButton
import cn.suwako.speedrun.ui.theme.SpeedRunTheme
import cn.suwako.speedrun.ui.theme.Theme
import cn.suwako.speedrun.ui.theme.ThemeDescription
import cn.suwako.speedrun.ui.theme.ThemeLiveData
import kotlinx.coroutines.launch

@Composable
fun AppSetting() {
    val navController = LocalNavController.current
    val sharedPreferences = LocalSharePreferences.current
    val originTheme = sharedPreferences.getInt("theme", Theme.DEFAULT.ordinal).let { Theme.values()[it] }

    val scope = rememberCoroutineScope()

    var showThemeSetting by rememberSaveable { mutableStateOf(false) }
    var nowSelectTheme by rememberSaveable { mutableStateOf(originTheme) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "应用设置") },
                navigationIcon = { BackIconButton(navController)},
                actions = {
                    Button(
                        onClick = {
                            scope.launch {
                                sharedPreferences.edit()
                                    .putInt("theme", nowSelectTheme.ordinal)
                                    .apply()
                                ThemeLiveData.postValue(nowSelectTheme)
                            }
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
                Modifier
                    .padding(10.dp),
            ) {
                Text(
                    text = "设置主题： ${ThemeDescription[nowSelectTheme]}",
                    modifier = Modifier
                        .clickable {
                            showThemeSetting = true
                        }.fillMaxWidth()
                        .padding(10.dp),
                )
                DropdownMenu(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    expanded = showThemeSetting,
                    onDismissRequest = { showThemeSetting = false }
                ) {
                    ThemeDescription.forEach { (theme, description) ->
                        DropdownMenuItem(onClick = {
                            nowSelectTheme = theme
                            showThemeSetting = false
                        }) {
                            Text(text = description)
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AppSettingPreview() {
    SpeedRunTheme {
        CompositionLocalProvider(LocalNavController provides rememberNavController()) {
            AppSetting()
        }
    }
}