package cn.suwako.speedrun.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import cn.suwako.speedrun.R
import cn.suwako.speedrun.ui.components.AccountView
import cn.suwako.speedrun.ui.components.DataView
import cn.suwako.speedrun.ui.components.RunView

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
            onClick = { navController.navigate("run") {
                popUpTo(navController.graph.startDestinationId) { inclusive = false }
            } },
            icon = { Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.materialsymbolsdirectionsrunrounded),
                contentDescription = "RunIcon") },
            label = { Text(text = "Run") }
        )
        BottomNavigationItem(
            selected = currentRoute == "data",
            onClick = { navController.navigate("data") {
                popUpTo(navController.graph.startDestinationId) { inclusive = false }
            } },
            icon = { Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.materialsymbolschartdatarounded),
                contentDescription = "DataIcon") },
            label = { Text(text = "Data") }
        )
        BottomNavigationItem(
            selected = currentRoute == "account",
            onClick = { navController.navigate("account") {
                popUpTo(navController.graph.startDestinationId) { inclusive = false }
            } },
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
                composable("run") { RunView() }
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