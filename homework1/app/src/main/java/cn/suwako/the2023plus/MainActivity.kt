package cn.suwako.the2023plus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cn.suwako.the2023plus.ui.theme.The2023PlusTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            The2023PlusTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    mainWindow()
                }
            }
        }
    }
}

@Composable
fun mainWindow(modifier: Modifier = Modifier,mainViewModel: MainViewModel = viewModel()) {
    Scaffold(
        topBar = { topBar() },
        bottomBar = { bottomBar(mainViewModel) },
        modifier = modifier.fillMaxSize(),
    ) { innerPadding ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            gameWindow(modifier, mainViewModel)
            AnimatedVisibility (
                visible = mainViewModel.gameOver,
                enter = fadeIn(
                    animationSpec = tween(1000)
                ),
                exit = fadeOut(
                    targetAlpha = 0f,
                    animationSpec = tween(100)
                )
            ) {
                gameOverSurface(modifier)
            }
        }
    }
}

@Composable
fun gameWindow(modifier: Modifier = Modifier, mainViewModel: MainViewModel = viewModel()) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround,
        modifier = modifier
            .fillMaxSize(),
    ) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(90.dp),
            modifier = Modifier
                .padding(0.dp, 0.dp, 0.dp, 0.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            items(mainViewModel.idiomCount * 4) {
                val color =
                    if (mainViewModel.buttonData[it].value.color == 0)
                        MaterialTheme.colors.primary
                    else MaterialTheme.colors.secondary;
                val colorState by animateColorAsState(
                    targetValue = color,
                    animationSpec = tween(400),
                )
                Button(
                    enabled = mainViewModel.buttonData[it].value.clickable,
                    onClick = { mainViewModel.clickButton(idx = it) },
                    modifier = modifier
                        .padding(5.dp, 15.dp)
                        .height(90.dp)
                    ,
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = colorState,
                    )
                ) {
                    if (!mainViewModel.buttonData[it].value.clickable || mainViewModel.buttonData[it].value.clicked) {
                        Text(
                            text = mainViewModel.buttonData[it].value.text,
                            fontSize = MaterialTheme.typography.h4.fontSize,
                        )
                    } else {
                        // use R.Drawable.maoyu
                        Image(
                            painter = painterResource(id = R.drawable.maoyu),
                            contentDescription = "maoyu",
                            modifier = Modifier
                                .fillMaxSize(),
                            contentScale = ContentScale.Fit,
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun gameOverSurface(modifier: Modifier = Modifier) {
    Box (
        modifier = modifier
            .fillMaxSize(),
    ) {
        Surface (
            modifier = modifier
                .fillMaxSize()
                .alpha(0.5f),
            color = MaterialTheme.colors.background,
            elevation = 8.dp,
        ) { }
        Column(
            modifier = modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround,
        ) {
            Text(
                text = "游戏结束",
                modifier = Modifier,
                textAlign = TextAlign.Center,
                fontSize = MaterialTheme.typography.h4.fontSize,
            )
        };
    }
}

@Composable
fun topBar() {
    TopAppBar(
        title = {
            Text("贰零二叁 Plus")
        },
    )
}

@Composable
fun bottomBar(mainViewModel: MainViewModel = viewModel()) {
    BottomAppBar(
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Button(
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.secondary,
                ),
                modifier = Modifier
                    .fillMaxWidth(0.5f),
                onClick = { mainViewModel.reset() },
            ) {
                Text("重新开始")
            }
            Text(
                text = "点击次数：${mainViewModel.clickCount}",
                modifier = Modifier,
                textAlign = TextAlign.Center,
                fontSize = MaterialTheme.typography.h6.fontSize,
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    The2023PlusTheme {
        mainWindow()
    }
}