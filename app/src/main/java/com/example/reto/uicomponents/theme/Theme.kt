package com.example.reto.uicomponents.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val BluePrimary = Color(0xFF3F51B5)
val BlueLight = Color(0xFF757DE8)
val BlueDark = Color(0xFF002984)
val TextWhite = Color(0xFFFFFFFF)

private val LightColorPalette = lightColorScheme(
    primary = BluePrimary,
    primaryContainer = BlueDark,
    onPrimary = TextWhite,
    background = Color.White,
    surface = BlueLight,
    onBackground = Color.Black,
    onSurface = Color.Black
)

private val DarkColorPalette = darkColorScheme(
    primary = BluePrimary,
    primaryContainer = BlueDark,
    onPrimary = TextWhite,
    background = Color.Black,
    surface = BlueLight,
    onBackground = Color.White,
    onSurface = Color.White
)

@Composable
fun AbogadosForoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        typography = Typography, // Aquí se importa la tipografía
        shapes = Shapes,         // Aquí se importan las formas
        content = content
    )
}
