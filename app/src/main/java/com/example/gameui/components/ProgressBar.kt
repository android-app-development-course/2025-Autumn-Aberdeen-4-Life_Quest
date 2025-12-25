package com.example.gameui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.gameui.ui.theme.GameColors

@Composable
fun GameProgressBar(
    progress: Float,
    modifier: Modifier = Modifier,
    height: Dp = 8.dp,
    backgroundColor: Color = GameColors.BorderColor,
    progressColors: List<Color> = listOf(GameColors.AccentGreen, GameColors.AccentBlue)
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .clip(RoundedCornerShape(height / 2))
            .background(backgroundColor)
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(progress. coerceIn(0f, 1f))
                .clip(RoundedCornerShape(height / 2))
                .background(
                    brush = Brush.horizontalGradient(colors = progressColors)
                )
        )
    }
}