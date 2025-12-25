package com.example.gameui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime. Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose. ui.graphics. Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font. FontWeight
import androidx.compose. ui.unit.dp
import androidx.compose. ui.unit.sp
import com.example.gameui.ui. theme.GameColors

@Composable
fun GameButton(
    text: String,
    onClick: () -> Unit,
    modifier:  Modifier = Modifier,
    gradientColors: List<Color> = listOf(GameColors.GradientStart, GameColors.GradientEnd)
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(
                brush = Brush.horizontalGradient(colors = gradientColors)
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 24.dp, vertical = 12.dp),
        contentAlignment = Alignment. Center
    ) {
        Text(
            text = text,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
    }
}