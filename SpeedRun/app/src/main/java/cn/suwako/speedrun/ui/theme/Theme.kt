package cn.suwako.speedrun.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.MutableLiveData

private val DarkColorPalette = darkColors(
    primary = Purple200,
    primaryVariant = Purple700,
    secondary = Teal200
)

private val LightColorPalette = lightColors(
    primary = Purple500,
    primaryVariant = Purple700,
    secondary = Teal200

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

private val AliceColorPalette = lightColors(
    primary = Aqua,
    primaryVariant = BrightTurquoise,
    secondary = Aquamarine,
    secondaryVariant = MediumAquaMarine,
    background = LavenderBlush,
    surface = AliceBlue,
    error = DeepSkyBlue,
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    onError = Color.Black,
)

private val RedColors = lightColors(
    primary = Red700,
    primaryVariant = Red900,
    onPrimary = Color.White,
    secondary = Red700,
    secondaryVariant = Red900,
    onSecondary = Color.White,
    error = Red800
)

enum class Theme {
    DEFAULT, DARK, ALICE, RED
}

val ThemeDescription = mapOf(
    Theme.DEFAULT to "默认",
    Theme.DARK to "深色",
    Theme.ALICE to "爱丽丝",
    Theme.RED to "红色"
)

val ThemeLiveData = MutableLiveData(Theme.DEFAULT)

@Composable
fun SpeedRunTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    var theme by rememberSaveable { mutableStateOf(Theme.DEFAULT) }

    ThemeLiveData.observeForever {
        theme = it
    }

    val colors = if (darkTheme) {
        DarkColorPalette
    } else when(theme) {
        Theme.DEFAULT -> LightColorPalette
        Theme.ALICE -> AliceColorPalette
        Theme.RED -> RedColors
        Theme.DARK -> DarkColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
    ) {
        ButtonDefaults.buttonColors(
            backgroundColor = colors.primary,
            contentColor = colors.onPrimary
        )
        content()
    }
}