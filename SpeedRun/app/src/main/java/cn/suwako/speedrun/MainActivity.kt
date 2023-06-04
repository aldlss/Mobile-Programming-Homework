package cn.suwako.speedrun

import android.content.SharedPreferences
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import cn.suwako.speedrun.ui.screens.*
import cn.suwako.speedrun.ui.theme.SpeedRunTheme
import java.lang.Readable

val LocalNavController = compositionLocalOf<NavController> { error("No NavController found") }
val LocalSharedPreferences = staticCompositionLocalOf<SharedPreferences> {
    error("No SharedPreferences provided")
}


class MainActivity : ComponentActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferences = getPreferences(MODE_PRIVATE)

        setContent {
            SpeedRunTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    CompositionLocalProvider(LocalSharedPreferences provides sharedPreferences) {
                        MainApp()
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