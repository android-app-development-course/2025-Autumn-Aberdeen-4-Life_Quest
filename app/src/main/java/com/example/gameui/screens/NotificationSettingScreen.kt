package com.example.gameui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gameui.ui.theme. GameColors

@Composable
fun NotificationSettingsScreen(
    onBackClick: () -> Unit
) {
    var taskReminder by remember { mutableStateOf(true) }
    var levelUp by remember { mutableStateOf(true) }
    var bonusReward by remember { mutableStateOf(true) }
    var dailyReport by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GameColors.BackgroundLight)
    ) {
        SettingTopBar(title = "通知设置", onBackClick = onBackClick)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Text(
                text = "推送通知",
                color = GameColors.TextSecondary,
                fontSize = 13.sp,
                modifier = Modifier.padding(horizontal = 4.dp, vertical = 8.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(2.dp, RoundedCornerShape(16.dp))
                    .clip(RoundedCornerShape(16.dp))
                    .background(GameColors. CardBackground)
            ) {
                Column {
                    SwitchItem(
                        title = "任务提醒",
                        subtitle = "任务截止前提醒",
                        checked = taskReminder,
                        onCheckedChange = { taskReminder = it }
                    )
                    Divider(color = GameColors.BorderLight)
                    SwitchItem(
                        title = "升级通知",
                        subtitle = "等级提升时通知",
                        checked = levelUp,
                        onCheckedChange = { levelUp = it }
                    )
                    Divider(color = GameColors.BorderLight)
                    SwitchItem(
                        title = "奖励通知",
                        subtitle = "获得额外奖励时通知",
                        checked = bonusReward,
                        onCheckedChange = { bonusReward = it }
                    )
                    Divider(color = GameColors.BorderLight)
                    SwitchItem(
                        title = "每日报告",
                        subtitle = "每天晚上发送进度报告",
                        checked = dailyReport,
                        onCheckedChange = { dailyReport = it }
                    )
                }
            }
        }
    }
}

@Composable
private fun SwitchItem(
    title:  String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
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
            Spacer(modifier = Modifier.height(4.dp))
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