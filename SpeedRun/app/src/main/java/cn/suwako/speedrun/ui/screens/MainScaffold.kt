package cn.suwako.speedrun.ui.screens

import android.widget.Toast
import androidx.activity.addCallback
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import cn.suwako.speedrun.LocalNavController
import cn.suwako.speedrun.MainActivity
import cn.suwako.speedrun.R

@Composable
fun MainTopBar()
{
    TopAppBar(modifier = Modifier, title = { Text(text = "SpeedRun") })
}

@Composable
fun currentRoute(navController: NavController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}

@Composable
fun MainBottomBar(navController: NavController)
{
    BottomAppBar(
        modifier = Modifier,
    ) {
        val currentRoute = currentRoute(navController)
        BottomNavigationItem(
            selected = currentRoute == "run",
            onClick = {
                if (currentRoute != "run") {
                    navController.navigate("run") {
                        popUpTo(navController.graph.id) { inclusive = true }
                    }
                }
            },
            icon = { Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.materialsymbolsdirectionsrunrounded),
                contentDescription = "RunIcon") },
            label = { Text(text = "Run") }
        )
        BottomNavigationItem(
            selected = currentRoute == "data",
            onClick = {
                if (currentRoute != "data") {
                    navController.navigate("data") {
                        popUpTo(navController.graph.id) { inclusive = true }
                    }
                }
            },
            icon = { Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.materialsymbolschartdatarounded),
                contentDescription = "DataIcon") },
            label = { Text(text = "Data") }
        )
        BottomNavigationItem(
            selected = currentRoute == "account",
            onClick = {
                if(currentRoute != "account") {
                    navController.navigate("account") {
                        popUpTo(navController.graph.id) { inclusive = true }
                    }
                }
            },
            icon = { Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.materialsymbolsaccountcirclesharp),
                contentDescription = "AccountIcon") },
            label = { Text(text = "Account") }
        )
    }
}

@Composable
fun MainScaffold() {
    val navController = rememberNavController()

    // 敏捷开发的成果，按两次返回键退出程序
    val context = LocalContext.current as MainActivity
    val outerNavController = LocalNavController.current
    var lastBackTime = remember { 0L }
    // 加一个副作用，这样应该可以重组时不会重复添加回调
    LaunchedEffect(key1 = context) {
        context.onBackPressedDispatcher.addCallback {
            // 非常不优雅写法，这就是敏捷开发的魅力（虽然也琢磨了挺久）
            if (outerNavController.currentBackStackEntry?.destination?.route == "main") {
                if (System.currentTimeMillis() - lastBackTime < 2000) {
                    context.finish()
                } else {
                    lastBackTime = System.currentTimeMillis()
                    Toast.makeText(context, "再按一次退出", Toast.LENGTH_SHORT).show()
                }
            } else {
                // 重点危险代码
                outerNavController.navigateUp()
            }
        }
    }

    Scaffold(
        modifier = Modifier,
        topBar = { MainTopBar() },
        bottomBar = { MainBottomBar(navController) },
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            NavHost(navController, startDestination = "run") {
                composable("run") { RunViewEntry() }
                composable("data") { DataView() }
                composable("account") { AccountView() }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainScaffoldPreview()
{
    MainScaffold()
}