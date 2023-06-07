package cn.suwako.speedrun

import android.content.SharedPreferences
import android.graphics.Bitmap
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.room.Room
import cn.suwako.speedrun.data.local.database.AppDatabase
import cn.suwako.speedrun.ui.screens.*
import cn.suwako.speedrun.ui.theme.SpeedRunTheme
import cn.suwako.speedrun.ui.utils.PermissionManager
import java.lang.Readable

val LocalNavController = compositionLocalOf<NavController> { error("No NavController found") }
val LocalSharedPreferences = staticCompositionLocalOf<SharedPreferences> {
    error("No SharedPreferences provided")
}
val LocalPermissionManager = staticCompositionLocalOf<PermissionManager> {
    error("No SharedPreferences provided")
}


class MainActivity : ComponentActivity() {
    companion object {
        lateinit var database: AppDatabase
            private set
    }
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var permissionManager: PermissionManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferences = getPreferences(MODE_PRIVATE)
        permissionManager = PermissionManager(this)


        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "speed_run"
        ).build()

        setContent {
            SpeedRunTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    CompositionLocalProvider(LocalSharedPreferences provides sharedPreferences) {
                        CompositionLocalProvider(LocalPermissionManager provides permissionManager) {

                            MainApp()
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
                "detail/{date}",
                arguments = listOf(navArgument("date") {
                    type = NavType.StringType
                })
            ) { backStackEntry ->
                val dataValue = backStackEntry.arguments?.getString("date")
                dataValue?.let {
                    DetailScreen(navController, it)
                }
            }
            composable(
                "accountOverview"
            ) {
                AccountOverview()
            }
            composable("login") {
                LoginScreen()
            }
            composable("register") {
                RegisterScreen()
            }
            composable("appSetting") {
                AppSetting(navController)
            }
            composable("camera") {
                CameraScreen()
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