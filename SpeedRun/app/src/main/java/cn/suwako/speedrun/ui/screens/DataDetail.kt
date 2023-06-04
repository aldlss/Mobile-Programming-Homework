package cn.suwako.speedrun.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import cn.suwako.speedrun.R

@Composable
fun DetailScreen(navController: NavController, date: String) {
    // 根据传递的参数显示详细数据
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .fillMaxWidth(),
                title = { Text("$date 详细数据") },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            // 返回上一级
                            navController.popBackStack()
                        },
                        content = {
                            Icon(
                                imageVector = ImageVector.vectorResource(id = R.drawable.materialsymbolsarrowbackrounded),
                                contentDescription = "BackIcon"
                            )
                        }
                    )
                }
            )
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            verticalArrangement = Arrangement.SpaceAround,
        ) {
            Image(
                modifier = Modifier
                    .fillMaxWidth(),
                contentDescription = null,
                painter = painterResource(id = R.drawable.ic_launcher_background)
            )
            val showList = listOf(
                Pair("数据1", 11212),
                Pair("数据2", 11212),
                Pair("数据3", 11212),
                Pair("数据4", 11212),
                Pair("数据5", 11212),
                Pair("数据6", 11212),
                Pair("数据7", 11212),
            )
            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.8f)
                    .padding(10.dp),
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp),
            ) {
                items(showList.size) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                1.dp,
                                androidx.compose.ui.graphics.Color.Black,
                                shape = androidx.compose.foundation.shape.RoundedCornerShape(10.dp)
                            ),
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
                        ) {
                            Text(
                                text = showList[it].first,
                                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                            )
                            Text(
                                text = showList[it].second.toString(),
                            )
                        }
                    }
                }
            }
        }
    }
}
