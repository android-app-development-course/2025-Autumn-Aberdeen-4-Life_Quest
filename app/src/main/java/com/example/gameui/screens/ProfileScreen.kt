package com.example.gameui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation. clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation. shape.CircleShape
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gameui.ui.theme.GameColors

@Composable
fun ProfileScreen(
    gameState: GameState,
    onBackClick: () -> Unit,
    onNavigateToEdit: () -> Unit,
    onNavigateToNotification: () -> Unit,
    onNavigateToPrivacy: () -> Unit,
    onNavigateToHelp: () -> Unit,
    onNavigateToAbout: () -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GameColors. BackgroundLight)
    ) {
        ProfileHeaderCard(
            level = gameState.level,
            username = gameState.username,
            experience = gameState.experience,
            maxExperience = gameState.maxExperience,
            onBackClick = onBackClick
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(16.dp)
        ) {
            ResourceStatsCard(
                stars = gameState.stars,
                physicalAttr = gameState.physicalAttr,
                intelligenceAttr = gameState.intelligenceAttr,
                cultivationAttr = gameState.cultivationAttr
            )

            Spacer(modifier = Modifier.height(16.dp))

            TaskStatsCard(
                totalTasks = gameState.tasks.size,
                completedTasks = gameState.tasks.count { it.isCompleted },
                bonusClaimedCount = gameState.bonusClaimedCount
            )

            Spacer(modifier = Modifier.height(16.dp))

            SettingsCard(
                onNavigateToEdit = onNavigateToEdit,
                onNavigateToNotification = onNavigateToNotification,
                onNavigateToPrivacy = onNavigateToPrivacy,
                onNavigateToHelp = onNavigateToHelp,
                onNavigateToAbout = onNavigateToAbout
            )

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
private fun ProfileHeaderCard(
    level: Int,
    username: String,
    experience: Int,
    maxExperience: Int,
    onBackClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
                .background(
                    brush = Brush. verticalGradient(
                        colors = listOf(
                            GameColors.AccentPurple,
                            GameColors. AccentBlue
                        )
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier. fillMaxWidth(),
                horizontalArrangement = Arrangement. SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        . clip(RoundedCornerShape(10.dp))
                        .background(Color.White.copy(alpha = 0.2f))
                        .clickable { onBackClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons. Default.ChevronLeft,
                        contentDescription = "返回",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Text(
                    text = "个人中心",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                Box(modifier = Modifier.size(40.dp))
            }

            Spacer(modifier = Modifier. height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .border(4.dp, Color.White, CircleShape)
                        .background(Color.White.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(48.dp)
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(
                        text = username,
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(GameColors.AccentYellow)
                            .padding(horizontal = 12.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "LV.  $level",
                            color = GameColors.TextPrimary,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier. height(20.dp))

            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "经验值",
                        color = Color. White. copy(alpha = 0.9f),
                        fontSize = 13.sp
                    )
                    Text(
                        text = "$experience / $maxExperience",
                        color = Color.White,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .background(Color.White.copy(alpha = 0.2f))
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(experience. toFloat() / maxExperience)
                            .clip(RoundedCornerShape(5.dp))
                            .background(
                                brush = Brush.horizontalGradient(
                                    colors = listOf(
                                        GameColors.AccentYellow,
                                        GameColors.AccentOrange
                                    )
                                )
                            )
                    )
                }
            }
        }
    }
}

@Composable
private fun ResourceStatsCard(
    stars:  Int,
    physicalAttr: Int,
    intelligenceAttr: Int,
    cultivationAttr: Int
) {
    Column {
        SectionTitle(title = "资源统计")

        Spacer(modifier = Modifier. height(12.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(4.dp, RoundedCornerShape(16.dp))
                .clip(RoundedCornerShape(16.dp))
                .background(GameColors.CardBackground)
                .padding(16.dp)
        ) {
            Column {
                StatItem(
                    icon = Icons.Default.Star,
                    iconColor = GameColors.AccentYellow,
                    label = "星星",
                    value = stars. toString()
                )

                Divider(
                    modifier = Modifier.padding(vertical = 12.dp),
                    color = GameColors.BorderLight
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    AttributeStatItem(
                        label = "体质",
                        value = physicalAttr,
                        color = GameColors. AttrPhysical
                    )
                    AttributeStatItem(
                        label = "智力",
                        value = intelligenceAttr,
                        color = GameColors.AccentBlue
                    )
                    AttributeStatItem(
                        label = "修养",
                        value = cultivationAttr,
                        color = GameColors.AccentOrange
                    )
                }
            }
        }
    }
}

@Composable
private fun TaskStatsCard(
    totalTasks: Int,
    completedTasks: Int,
    bonusClaimedCount: Int
) {
    Column {
        SectionTitle(title = "任务统计")

        Spacer(modifier = Modifier.height(12.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(4.dp, RoundedCornerShape(16.dp))
                .clip(RoundedCornerShape(16.dp))
                .background(GameColors.CardBackground)
                .padding(16.dp)
        ) {
            Column {
                StatItem(
                    icon = Icons. Outlined.Assignment,
                    iconColor = GameColors.AccentPurple,
                    label = "总任务数",
                    value = totalTasks.toString()
                )

                Divider(
                    modifier = Modifier.padding(vertical = 12.dp),
                    color = GameColors.BorderLight
                )

                StatItem(
                    icon = Icons.Default.CheckCircle,
                    iconColor = GameColors.AccentGreen,
                    label = "已完成",
                    value = completedTasks.toString()
                )

                Divider(
                    modifier = Modifier.padding(vertical = 12.dp),
                    color = GameColors.BorderLight
                )

                StatItem(
                    icon = Icons.Default.CardGiftcard,
                    iconColor = GameColors.AccentYellow,
                    label = "额外奖励领取次数",
                    value = "${bonusClaimedCount}次"
                )

                Divider(
                    modifier = Modifier.padding(vertical = 12.dp),
                    color = GameColors.BorderLight
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "完成率",
                        color = GameColors.TextSecondary,
                        fontSize = 14.sp
                    )

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = if (totalTasks > 0) {
                                String.format("%.1f%%", (completedTasks.toFloat() / totalTasks) * 100)
                            } else {
                                "0%"
                            },
                            color = GameColors.AccentGreen,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SettingsCard(
    onNavigateToEdit: () -> Unit,
    onNavigateToNotification: () -> Unit,
    onNavigateToPrivacy: () -> Unit,
    onNavigateToHelp: () -> Unit,
    onNavigateToAbout:  () -> Unit
) {
    Column {
        SectionTitle(title = "设置")

        Spacer(modifier = Modifier.height(12.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(4.dp, RoundedCornerShape(16.dp))
                .clip(RoundedCornerShape(16.dp))
                .background(GameColors.CardBackground)
        ) {
            Column {
                SettingItem(
                    icon = Icons. Outlined.Edit,
                    title = "编辑资料",
                    onClick = onNavigateToEdit
                )

                Divider(color = GameColors.BorderLight)

                SettingItem(
                    icon = Icons.Outlined. Notifications,
                    title = "通知设置",
                    onClick = onNavigateToNotification
                )

                Divider(color = GameColors.BorderLight)

                SettingItem(
                    icon = Icons. Outlined.Security,
                    title = "隐私设置",
                    onClick = onNavigateToPrivacy
                )

                Divider(color = GameColors.BorderLight)

                SettingItem(
                    icon = Icons.Outlined.Help,
                    title = "帮助与反馈",
                    onClick = onNavigateToHelp
                )

                Divider(color = GameColors.BorderLight)

                SettingItem(
                    icon = Icons.Outlined. Info,
                    title = "关于我们",
                    onClick = onNavigateToAbout
                )
            }
        }
    }
}

@Composable
private fun StatItem(
    icon: ImageVector,
    iconColor: Color,
    label: String,
    value: String
) {
    Row(
        modifier = Modifier. fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(iconColor. copy(alpha = 0.15f)),
                contentAlignment = Alignment. Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconColor,
                    modifier = Modifier. size(20.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = label,
                color = GameColors.TextSecondary,
                fontSize = 14.sp
            )
        }

        Text(
            text = value,
            color = GameColors.TextPrimary,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun AttributeStatItem(
    label:  String,
    value: Int,
    color: Color
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
                .background(color.copy(alpha = 0.15f)),
            contentAlignment = Alignment. Center
        ) {
            Text(
                text = value.toString(),
                color = color,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = label,
            color = GameColors.TextSecondary,
            fontSize = 13.sp
        )
    }
}

@Composable
private fun SettingItem(
    icon:  ImageVector,
    title: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = GameColors.AccentPurple,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier. width(16.dp))

        Text(
            text = title,
            color = GameColors.TextPrimary,
            fontSize = 15.sp,
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

@Composable
private fun SectionTitle(title: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .width(4.dp)
                .height(18.dp)
                .clip(RoundedCornerShape(2.dp))
                .background(GameColors.AccentPurple)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = title,
            color = GameColors. TextPrimary,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}