package com.example.gameui.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightGameColorScheme = lightColorScheme(
    primary = GameColors.AccentPurple,
    secondary = GameColors.AccentBlue,
    tertiary = GameColors. AccentOrange,
    background = GameColors.BackgroundLight,
    surface = GameColors.CardBackground,
    onPrimary = GameColors.TextWhite,
    onSecondary = GameColors.TextWhite,
    onTertiary = GameColors.TextWhite,
    onBackground = GameColors.TextPrimary,
    onSurface = GameColors.TextPrimary
)

@Composable
fun GameUITheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightGameColorScheme,
        content = content
    )
}