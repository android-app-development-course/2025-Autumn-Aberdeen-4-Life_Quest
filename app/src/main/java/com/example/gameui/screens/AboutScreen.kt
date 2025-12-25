package com.example.gameui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gameui.ui.theme.GameColors

@Composable
fun AboutScreen(
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GameColors.BackgroundLight)
    ) {
        SettingTopBar(title = "关于我们", onBackClick = onBackClick)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier. height(32.dp))

            // 应用图标
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(GameColors.AccentPurple),
                contentAlignment = Alignment. Center
            ) {
                Icon(
                    imageVector = Icons.Default.EmojiEvents,
                    contentDescription = null,
                    tint = GameColors.AccentYellow,
                    modifier = Modifier.size(60.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "游戏化人生",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = GameColors.TextPrimary
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Version 1.0.0",
                fontSize = 14.sp,
                color = GameColors.TextSecondary
            )

            Spacer(modifier = Modifier.height(32.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(2.dp, RoundedCornerShape(16.dp))
                    . clip(RoundedCornerShape(16.dp))
                    .background(GameColors.CardBackground)
                    .padding(24.dp)
            ) {
                Column {
                    Text(
                        text = "将生活游戏化，让每一天都充满动力！",
                        fontSize = 15.sp,
                        color = GameColors.TextPrimary,
                        textAlign = TextAlign.Center,
                        lineHeight = 22.sp
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "通过完成日常任务，提升自己的各项属性，记录成长的每一步。",
                        fontSize = 13.sp,
                        color = GameColors.TextSecondary,
                        textAlign = TextAlign.Center,
                        lineHeight = 20.sp
                    )
                }
            }

            Spacer(modifier = Modifier. height(24.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(2.dp, RoundedCornerShape(16.dp))
                    . clip(RoundedCornerShape(16.dp))
                    .background(GameColors.CardBackground)
            ) {
                Column {
                    InfoItem(label = "开发者", value = "GameUI Team")
                    Divider(color = GameColors. BorderLight)
                    InfoItem(label = "官方网站", value = "www.gameui.com")
                    Divider(color = GameColors.BorderLight)
                    InfoItem(label = "联系邮箱", value = "contact@gameui.com")
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "© 2024 GameUI.  All rights reserved.",
                fontSize = 12.sp,
                color = GameColors.TextMuted
            )
        }
    }
}

@Composable
private fun InfoItem(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            color = GameColors.TextSecondary,
            fontSize = 14.sp
        )
        Text(
            text = value,
            color = GameColors. TextPrimary,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
    }
}