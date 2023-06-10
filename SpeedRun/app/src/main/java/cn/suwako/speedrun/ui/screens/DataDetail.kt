package cn.suwako.speedrun.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import cn.suwako.speedrun.R
import cn.suwako.speedrun.ui.components.BackIconButton
import cn.suwako.speedrun.ui.components.EnsureAlertDialog
import cn.suwako.speedrun.ui.viewmodel.DataDetailViewModel

@Composable
fun DetailScreen(navController: NavController, runDataId: Int, dataDetailViewModel: DataDetailViewModel = viewModel()) {

    LaunchedEffect(key1 = runDataId) {
        dataDetailViewModel.requestRunData(runDataId)
    }

    LaunchedEffect(Unit) {
        dataDetailViewModel.deleteSuccess.collect {
            if (it) {
                // 直到现在才知道 navController 还有 context 的 = =
                Toast.makeText(navController.context, "删除成功", Toast.LENGTH_SHORT).show()
                navController.popBackStack()
            } else {
                Toast.makeText(navController.context, "删除失败", Toast.LENGTH_SHORT).show()
            }
        }
    }

    var isDeleteEnsureDialogShow by remember { mutableStateOf(false) }
    if (isDeleteEnsureDialogShow) {
        EnsureAlertDialog(
            text = "确认删除该数据吗？",
            onConfirm = {
                dataDetailViewModel.deleteRunData()
                isDeleteEnsureDialogShow = false
            },
            onCancel = {
                isDeleteEnsureDialogShow = false
            }
        )
    }

    // 根据传递的参数显示详细数据
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .fillMaxWidth(),
                title = { Text("详细数据") },
                navigationIcon = { BackIconButton(navController) },
                actions = {
                    IconButton(
                        onClick = {
                            isDeleteEnsureDialogShow = true
                        }
                    ) {
                        Icon(
                            painterResource(id = R.drawable.materialsymbolsdeleteoutlinerounded),
                            contentDescription = "删除",
                        )
                    }
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
            dataDetailViewModel.capturePicture ?. also { bitmap ->
                Image(
                    bitmap = bitmap.asImageBitmap(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .requiredHeight(200.dp),
                    contentDescription = "跑步图片",
                )
            } ?: Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .requiredHeight(200.dp),
                contentDescription = "无",
                painter = painterResource(id = R.drawable.materialsymbolsdirectionsrunrounded)
            )
            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp),
            ) {
                items(dataDetailViewModel.runDetails.size) { index ->
                    val title = dataDetailViewModel.runDetails[index].first
                    val value = dataDetailViewModel.runDetails[index].second
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                1.dp,
                                androidx.compose.ui.graphics.Color.Black,
                                shape = androidx.compose.foundation.shape.RoundedCornerShape(10.dp)
                            ).padding(5.dp),
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
                        ) {
                            Text(
                                text = title,
                                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                            )
                            Text(
                                text = value,
                            )
                        }
                    }
                }
            }
        }
    }
}
