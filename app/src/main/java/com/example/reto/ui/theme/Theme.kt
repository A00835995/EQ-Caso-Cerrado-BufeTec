package com.example.reto.ui.theme


import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Cyan
import androidx.compose.ui.graphics.Color.Companion.White

private val LightColors = lightColorScheme(
    primary = DarkestBlue,
    secondary = TecBlue,
    tertiary = MediumBlue,
    background = White,
    onPrimary = Color.White
)

private val DarkColors = darkColorScheme(
    primary = Cyan,
    secondary = MediumBlue,
    tertiary = TecBlue,
    background = DarkestBlue,
    onPrimary = Color.Black
)

@Composable
fun RetoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColors
    } else {
        LightColors
    }

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}