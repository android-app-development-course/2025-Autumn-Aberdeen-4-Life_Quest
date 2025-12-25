package com.example.gameui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime. Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics. Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.gameui.ui.theme.GameColors

@Composable
fun GameCard(
    modifier: Modifier = Modifier,
    glowColor: Color = GameColors. AccentGold,
    content: @Composable ColumnScope.() -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .border(
                width = 1.dp,
                brush = Brush.linearGradient(
                    colors = listOf(
                        glowColor.copy(alpha = 0.5f),
                        glowColor.copy(alpha = 0.1f)
                    )
                ),
                shape = RoundedCornerShape(16.dp)
            )
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        GameColors.CardBackground,
                        GameColors.CardBackground.copy(alpha = 0.8f)
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier. padding(16.dp),
            content = content
        )
    }
}