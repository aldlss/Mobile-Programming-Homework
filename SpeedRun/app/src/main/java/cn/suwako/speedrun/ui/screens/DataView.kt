package cn.suwako.speedrun.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cn.suwako.speedrun.ui.theme.SpeedRunTheme

import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import cn.suwako.speedrun.LocalNavController
import cn.suwako.speedrun.MainActivity
import cn.suwako.speedrun.ui.components.NoLoginView
import cn.suwako.speedrun.ui.viewmodel.DataViewModel

@Composable
fun DataView() {
    val navController = LocalNavController.current
    val isLoggedIn = MainActivity.authManager.getIsLoggedIn()

    if (isLoggedIn) {
        DataOverview(navController)
    } else {
        NoLoginView(navController)
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DataViewPreview() {
    SpeedRunTheme {
        DataView()
    }
}

@Composable
fun DataOverview(navController: NavController, dataViewModel: DataViewModel = viewModel()) {

    LaunchedEffect(MainActivity.authManager.getUserId()) {
        dataViewModel.loadData(MainActivity.authManager.getUserId())
    }

    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
            .border(2.dp, MaterialTheme.colors.secondary, MaterialTheme.shapes.medium),
        columns = GridCells.Fixed(1),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp)
    ) {
        dataViewModel.showData.forEach { (_, runDataItems) ->
            item {
                Text(
                    text = "${runDataItems[0].runDate.year} 年 ${runDataItems[0].runDate.monthValue} 月",
                    style = androidx.compose.ui.text.TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(16.dp)
                )
            }
            items(runDataItems) { dataItem ->
                Row(
                    modifier = Modifier
                        .padding(16.dp, 2.dp)
                        .clickable {
                            // 导航到详细数据界面
                            navController.navigate("detail/${dataItem.id}")
                        }.fillMaxWidth()
                        .fillMaxHeight(0.05f)
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "${dataItem.runDistance} 米")
                    Text(text = "${dataItem.runTime}")
                    Text(text = "%.1f 米/秒".format(dataItem.runSpeed))
                }
            }
        }
    }
}
