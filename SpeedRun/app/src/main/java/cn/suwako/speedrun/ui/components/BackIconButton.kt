package cn.suwako.speedrun.ui.components

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavController
import cn.suwako.speedrun.R

@Composable
fun BackIconButton(navController: NavController) {
    IconButton(
        onClick = { navController.navigateUp() },
        content = {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.materialsymbolsarrowbackrounded),
                contentDescription = "BackIcon"
            )
        }
    )
}