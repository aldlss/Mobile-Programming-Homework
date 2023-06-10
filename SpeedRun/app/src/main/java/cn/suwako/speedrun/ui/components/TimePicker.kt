package cn.suwako.speedrun.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import cn.suwako.speedrun.ui.theme.SpeedRunTheme
import java.time.LocalTime

@Composable
fun TimePicker(onCancel: () -> Unit, onTimePicked: (LocalTime) -> Unit) {
    Dialog(
        onDismissRequest = onCancel,
    ) {
        val hour = remember { mutableStateOf(0) }
        val minute = remember { mutableStateOf(0) }
        val second = remember { mutableStateOf(0) }

        Surface(
            modifier = Modifier,
            shape = MaterialTheme.shapes.medium,
        ) {
            Column(
                modifier = Modifier,
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "选择时间",
                    style = MaterialTheme.typography.h5
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    val listOfNumberPicker = mutableListOf(
                        Triple(hour, "时", 23),
                        Triple(minute, "分", 59),
                        Triple(second, "秒", 59),
                    )
                    listOfNumberPicker.forEach { (value, unit, canMaxValue) ->
                        Row(
                            modifier = Modifier
                                .wrapContentHeight()
                                .weight(1f),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            AndroidView(
                                factory = { context ->
                                    android.widget.NumberPicker(context).apply {
                                        minValue = 0
                                        maxValue = canMaxValue
                                        setOnValueChangedListener { _, _, newValue ->
                                            value.value = newValue
                                        }
                                    }
                                },
                                modifier = Modifier
                                    .width(100.dp)
                                    .height(100.dp)
                                    .weight(1f),
                            )
                            Text(
                                modifier = Modifier
                                    .weight(0.5f),
                                text = unit,
                                style = MaterialTheme.typography.h6,
                                textAlign = TextAlign.Center,
                            )
                        }
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "取消",
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier
                            .clickable {
                                onCancel()
                            },
                        textAlign = TextAlign.Center,
                    )
                    Text(
                        text = "确定",
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier
                            .clickable {
                                onTimePicked(LocalTime.of(hour.value, minute.value, second.value))
                                // 关于这里为什么不调用 onCancel()，我的评价是不要多管闲事，只负责调用 onTimePicked() 好吧
                            },
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun TimePickerPreview() {
    SpeedRunTheme {
        TimePicker({},{})
    }
}