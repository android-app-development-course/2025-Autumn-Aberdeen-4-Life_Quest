package com.example.gameui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gameui.ui.theme.GameColors

@Composable
fun PrivacySettingsScreen(
    onBackClick:  () -> Unit
) {
    var profileVisible by remember { mutableStateOf(true) }
    var taskVisible by remember { mutableStateOf(true) }
    var statsVisible by remember { mutableStateOf(false) }

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
                            GameColors. AccentBlue
                        )
                    )
                )
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
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

                Spacer(modifier = Modifier. width(16.dp))

                Text(
                    text = "隐私设置",
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
                .padding(16.dp)
        ) {
            Text(
                text = "数据可见性",
                color = GameColors.TextSecondary,
                fontSize = 13.sp,
                modifier = Modifier.padding(horizontal = 4.dp, vertical = 8.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(2.dp, RoundedCornerShape(16.dp))
                    . clip(RoundedCornerShape(16.dp))
                    .background(GameColors.CardBackground)
            ) {
                Column {
                    PrivacySwitchItem(
                        title = "公开资料",
                        subtitle = "允许他人查看你的个人资料",
                        checked = profileVisible,
                        onCheckedChange = { profileVisible = it }
                    )
                    Divider(color = GameColors. BorderLight)
                    PrivacySwitchItem(
                        title = "公开任务",
                        subtitle = "允许他人查看你的任务完成情况",
                        checked = taskVisible,
                        onCheckedChange = { taskVisible = it }
                    )
                    Divider(color = GameColors.BorderLight)
                    PrivacySwitchItem(
                        title = "公开统计",
                        subtitle = "允许他人查看你的详细数据",
                        checked = statsVisible,
                        onCheckedChange = { statsVisible = it }
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "账号安全",
                color = GameColors.TextSecondary,
                fontSize = 13.sp,
                modifier = Modifier.padding(horizontal = 4.dp, vertical = 8.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(2.dp, RoundedCornerShape(16.dp))
                    .clip(RoundedCornerShape(16.dp))
                    .background(GameColors.CardBackground)
            ) {
                Column {
                    SecurityItem(
                        title = "修改密码",
                        onClick = { }
                    )
                    Divider(color = GameColors. BorderLight)
                    SecurityItem(
                        title = "注销账号",
                        onClick = { }
                    )
                }
            }
        }
    }
}

@Composable
private fun PrivacySwitchItem(
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange:  (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                color = GameColors.TextPrimary,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier. height(4.dp))
            Text(
                text = subtitle,
                color = GameColors.TextSecondary,
                fontSize = 13.sp
            )
        }

        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = GameColors.AccentPurple,
                checkedTrackColor = GameColors.AccentPurple. copy(alpha = 0.5f)
            )
        )
    }
}

@Composable
private fun SecurityItem(
    title: String,
    onClick:  () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            color = GameColors.TextPrimary,
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(1f)
        )

        Icon(
            imageVector = Icons. Default.ChevronRight,
            contentDescription = null,
            tint = GameColors.TextMuted,
            modifier = Modifier.size(20.dp)
        )
    }
}