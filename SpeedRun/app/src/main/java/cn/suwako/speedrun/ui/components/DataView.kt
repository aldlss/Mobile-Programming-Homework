package cn.suwako.speedrun.ui.components

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

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import cn.suwako.speedrun.LocalNavController
import cn.suwako.speedrun.R
import cn.suwako.speedrun.ui.screens.AccountDetail
import cn.suwako.speedrun.ui.screens.AccountSetting
import cn.suwako.speedrun.ui.screens.DetailScreen
import cn.suwako.speedrun.ui.screens.LoggedinAccountOverview
import java.time.LocalDate
import java.time.Month

@Composable
fun DataView() {
    val navController = LocalNavController.current
    val sharedPref = cn.suwako.speedrun.LocalSharedPreferences.current
    val isLoggedIn = sharedPref.getBoolean(stringResource(R.string.IsLoggedIn), false)

    if (isLoggedIn) {
        MonthlyDataPanel(navController)
    } else {
        NologIndataView()
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
fun NologIndataView()
{
    val navController = LocalNavController.current
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "登录以查看数据",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )
        Button(
            onClick = {
                navController.navigate("login")
            }
        ) {
            Text(
                text = "登录",
                fontSize = 24.sp,
            )
        }
    }
}

data class DataItem(val date: LocalDate, val value: Int)

@Composable
fun MonthlyDataPanel(navController: NavController) {
    // 示例数据
    val dataList = listOf(
        DataItem(LocalDate.of(2023, Month.JANUARY, 10), 100),
        DataItem(LocalDate.of(2023, Month.JANUARY, 15), 150),
        DataItem(LocalDate.of(2023, Month.FEBRUARY, 20), 200),
        DataItem(LocalDate.of(2023, Month.MARCH, 30), 300),
        DataItem(LocalDate.of(2023, Month.MARCH, 25), 250),
        DataItem(LocalDate.of(2023, Month.MARCH, 5), 50)
    )

    // 按日期排序数据
    val sortedDataList = dataList.sortedBy { it.date }

    // 按月份分组
    val groupedData = sortedDataList.groupBy { it.date.month }

    // 创建一个包含月份和数据列表的Map
    val dataByMonth = groupedData.toSortedMap()

    LazyVerticalGrid(
        columns = GridCells.Fixed(1),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp)
    ) {
        dataByMonth.forEach { (month, dataItems) ->
            item {
                Text(
                    text = month.toString(),
                    style = androidx.compose.ui.text.TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(16.dp)
                )
            }
            items(dataItems) { dataItem ->
                Text(
                    text = "Value: ${dataItem.value}",
                    modifier = Modifier
                        .padding(16.dp, 0.dp)
                        .clickable {
                            // 导航到详细数据界面
                            navController.navigate("detail/${dataItem.date}") {
                                popUpTo(navController.graph.startDestinationId) {
                                    inclusive = false
                                }
                            }
                        }
                )
            }
        }
    }
}
