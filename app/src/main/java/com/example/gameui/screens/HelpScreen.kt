package com.example.gameui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gameui.ui.theme.GameColors

@Composable
fun HelpScreen(
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GameColors.BackgroundLight)
    ) {
        // 顶部栏
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .clip(RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
                .background(
                    brush = Brush. verticalGradient(
                        colors = listOf(
                            GameColors.AccentPurple,
                            GameColors.AccentBlue
                        )
                    )
                )
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier. fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.White.copy(alpha = 0.2f))
                        .clickable { onBackClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.ChevronLeft,
                        contentDescription = "返回",
                        tint = Color.White,
                        modifier = Modifier. size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    text = "帮助与反馈",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier. height(60.dp))

            // 邮件图标
            Box(
                modifier = Modifier
                    . size(80.dp)
                    .clip(RoundedCornerShape(20.dp))
                    . background(GameColors.AccentPurple. copy(alpha = 0.15f)),
                contentAlignment = Alignment. Center
            ) {
                Icon(
                    imageVector = Icons. Outlined.Email,
                    contentDescription = null,
                    tint = GameColors.AccentPurple,
                    modifier = Modifier.size(40.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "有问题联系这里",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = GameColors.TextPrimary,
                textAlign = TextAlign. Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            // 邮箱卡片
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(4.dp, RoundedCornerShape(16.dp))
                    .clip(RoundedCornerShape(16.dp))
                    .background(GameColors.CardBackground)
                    .padding(24.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // 邮件图标
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = null,
                        tint = GameColors.AccentBlue,
                        modifier = Modifier.size(32.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // 邮箱地址
                    Text(
                        text = "ABCDEFG@SCNU.com",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = GameColors.AccentPurple,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // 说明文字
                    Text(
                        text = "我们会在24小时内回复您的问题",
                        fontSize = 13.sp,
                        color = GameColors.TextSecondary,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(modifier = Modifier. height(32.dp))

            // 工作时间
            Text(
                text = "工作时间：周一至周五 9:00-18:00",
                fontSize = 12.sp,
                color = GameColors.TextMuted,
                textAlign = TextAlign.Center
            )
        }
    }
}