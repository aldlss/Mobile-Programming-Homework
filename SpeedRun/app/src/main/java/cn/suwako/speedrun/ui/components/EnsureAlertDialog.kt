package cn.suwako.speedrun.ui.components

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun EnsureAlertDialog(onConfirm: () -> Unit, onCancel: () -> Unit, onDismiss: () -> Unit = onCancel, text: String) {
    val buttonColors = ButtonDefaults.buttonColors(
        backgroundColor = MaterialTheme.colors.secondary,
        contentColor = MaterialTheme.colors.onSecondary
    )
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = text) },
        confirmButton = {
            Button(
                onClick = onConfirm,
                colors = buttonColors
            ) {
                Text(text = "确定")
            }
        },
        dismissButton = {
            Button(
                onClick = onCancel,
                colors = buttonColors
            ) {
                Text(text = "取消")
            }
        },
    )
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun EnsureAlertDialogPreview() {
    EnsureAlertDialog({},{},{}, "test")
}