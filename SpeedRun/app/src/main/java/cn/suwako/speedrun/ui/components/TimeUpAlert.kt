package cn.suwako.speedrun.ui.components

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import cn.suwako.speedrun.ui.theme.SpeedRunTheme
import cn.suwako.speedrun.ui.theme.Typography

@Composable
fun TimeUpAlert(onCancel: () -> Unit) {
    val context = LocalContext.current
    Dialog(
        onDismissRequest = {
            Toast.makeText(context, "请按确定键关闭", Toast.LENGTH_SHORT).show()
        },
    ) {
        Surface(
            modifier = Modifier,
            shape = MaterialTheme.shapes.medium,
        ) {
            Column(
                modifier = Modifier
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(3.dp)
            ) {
                Text(
                    text = "定时时间到",
                    style = Typography.h4,
                )
                Text(
                    modifier = Modifier
                        .clickable {
                        onCancel()
                    },
                    text = "确定",
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun TimeUpAlertPreview() {
    SpeedRunTheme {
        TimeUpAlert(onCancel = {})
    }
}