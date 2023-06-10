package cn.suwako.speedrun

import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import androidx.room.Room
import cn.suwako.speedrun.data.local.database.AppDatabase
import cn.suwako.speedrun.ui.screens.*
import cn.suwako.speedrun.ui.theme.SpeedRunTheme
import cn.suwako.speedrun.ui.domain.AuthManager
import cn.suwako.speedrun.ui.domain.GetPicture
import cn.suwako.speedrun.ui.domain.PermissionManager
import cn.suwako.speedrun.ui.theme.Theme
import cn.suwako.speedrun.ui.theme.ThemeLiveData

val LocalNavController = compositionLocalOf<NavController> { error("No NavController found") }

val LocalPermissionManager = staticCompositionLocalOf<PermissionManager> {
    error("No PermissionManager provided")
}

val LocalSharePreferences = staticCompositionLocalOf<SharedPreferences> {
    error("No SharedPreferences provided")
}

val LocalButtonColor = staticCompositionLocalOf<ButtonColors> {
    error("No ThemeDescription provided")
}

class MainActivity : ComponentActivity() {
    companion object {
        lateinit var database: AppDatabase
            private set
        lateinit var authManager: AuthManager
            private set
        lateinit var getPictureManager: GetPicture
            private set

    }
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var permissionManager: PermissionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferences = getPreferences(MODE_PRIVATE)
        authManager = AuthManager(sharedPreferences)

        permissionManager = PermissionManager(this)
        getPictureManager = GetPicture(this)


        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "speed_run"
        ).build()

        ThemeLiveData.value = Theme.values()[sharedPreferences.getInt("theme", Theme.DEFAULT.ordinal)]

        setContent {
            SpeedRunTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    CompositionLocalProvider(LocalContext provides this) {
                        CompositionLocalProvider(LocalPermissionManager provides permissionManager) {
                            CompositionLocalProvider(LocalSharePreferences provides sharedPreferences) {
                                CompositionLocalProvider(LocalButtonColor provides ButtonDefaults.buttonColors(
                                    backgroundColor = MaterialTheme.colors.secondary,
                                    contentColor = MaterialTheme.colors.onSecondary
                                )) {
                                    MainApp()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MainApp() {
    val navController = rememberNavController()
    CompositionLocalProvider(LocalNavController provides navController) {
        NavHost(navController = navController, startDestination = "main") {
            composable("main") {
                MainScaffold()
            }

            composable(
                "detail/{id}",
                arguments = listOf(navArgument("id") {
                    type = NavType.IntType
                })
            ) { backStackEntry ->
                val dataValue = backStackEntry.arguments?.getInt("id")
                dataValue?.let {
                    DetailScreen(navController, it)
                }
            }

            navigation(
                startDestination = "accountOverview",
                route = "account"
            ) {
                composable("accountOverview") {
                    AccountOverview()
                }
                composable("accountDetail") {
                    AccountDetail()
                }
                composable("accountSetting") {
                    AccountSetting()
                }
            }

            navigation(
                startDestination = "login",
                route = "auth"
            ) {
                composable("login") {
                    LoginScreen()
                }
                composable("register") {
                    RegisterScreen()
                }
            }

            composable("appSetting") {
                AppSetting()
            }

            composable("about") {
                AboutScreen()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SpeedRunTheme {
        MainScaffold()
    }
}