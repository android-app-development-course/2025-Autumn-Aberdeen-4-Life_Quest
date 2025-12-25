package com.example.gameui.screens

import androidx.compose.foundation. background
import androidx.compose.foundation. border
import androidx.compose.foundation. clickable
import androidx.compose. foundation.layout.*
import androidx. compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose. foundation.shape.RoundedCornerShape
import androidx.compose. foundation.verticalScroll
import androidx.compose.material. icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material. icons.outlined.*
import androidx.compose.material3.*
import androidx.compose. runtime.*
import androidx.compose. ui.Alignment
import androidx. compose.ui.Modifier
import androidx.compose.ui.draw. clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui. graphics. Brush
import androidx.compose.ui.graphics.Color
import androidx. compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx. compose.ui.unit.sp
import com.example.gameui.ui.theme.GameColors

@Composable
fun TaskListScreen(
    gameState: GameState,
    onBackClick: () -> Unit,
    onTaskComplete: (TaskData) -> Unit
) {
    var selectedFilter by remember { mutableStateOf("全部") }
    val scrollState = rememberScrollState()

    val allTasks = gameState.tasks

    // 筛选后的任务（用于显示）
    val filteredTasks = if (selectedFilter == "全部") {
        allTasks
    } else {
        allTasks.filter { it.category == selectedFilter }
    }

    val filteredInProgressTasks = filteredTasks. filter { ! it.isCompleted }
    val filteredCompletedTasks = filteredTasks.filter { it. isCompleted }

    // 总任务数据（用于进度和额外奖励，始终基于全部任务）
    val totalCompleted = allTasks.count { it.isCompleted }
    val totalTasks = allTasks.size

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GameColors.BackgroundLight)
    ) {
        TaskListTopBar(
            onBackClick = onBackClick,
            starCount = gameState.stars
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(16.dp)
        ) {
            TodayProgressCard(
                completed = totalCompleted,
                total = 8
            )
            Spacer(modifier = Modifier. height(16.dp))
            BonusRewardCard(
                current = totalCompleted % 5,
                target = 5,
                claimedCount = gameState.bonusClaimedCount
            )
            Spacer(modifier = Modifier.height(20.dp))
            FilterTabs(
                selectedFilter = selectedFilter,
                onFilterSelected = { selectedFilter = it }
            )

            Spacer(modifier = Modifier.height(20.dp))

            if (filteredInProgressTasks. isNotEmpty()) {
                TaskSectionHeader(title = "进行中", count = filteredInProgressTasks.size)
                Spacer(modifier = Modifier. height(12.dp))
                filteredInProgressTasks.forEach { task ->
                    TaskCard(
                        task = task,
                        onClick = { onTaskComplete(task) }
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // 已完成的任务
            if (filteredCompletedTasks.isNotEmpty()) {
                TaskSectionHeader(title = "已完成", count = filteredCompletedTasks.size)
                Spacer(modifier = Modifier. height(12.dp))
                filteredCompletedTasks. forEach { task ->
                    TaskCard(
                        task = task,
                        onClick = { }
                    )
                    Spacer(modifier = Modifier. height(12.dp))
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

private fun getCategoryColor(category: String): Color {
    return when (category) {
        "体质" -> GameColors.AttrPhysical
        "智力" -> GameColors.AccentBlue
        "修养" -> GameColors.AccentOrange
        else -> GameColors.AccentPurple
    }
}

@Composable
private fun TaskListTopBar(
    onBackClick: () -> Unit,
    starCount: Int
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        GameColors.AccentPurple,
                        GameColors.AccentBlue
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
                    imageVector = Icons. Default.ChevronLeft,
                    contentDescription = "返回",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = "任务列表",
                color = Color. White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons. Outlined.StarOutline,
                    contentDescription = null,
                    tint = GameColors.AccentYellow,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = starCount.toString(),
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun TodayProgressCard(completed: Int, total: Int) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp))
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        GameColors.AccentPurple,
                        GameColors.AccentBlue
                    )
                )
            )
            .padding(16.dp)
    ) {
        Column {
            Row(
                modifier = Modifier. fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "今日进度",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "$completed / $total",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp))
                    . background(Color.White.copy(alpha = 0.3f))
            ) {
                Box(
                    modifier = Modifier
                        . fillMaxHeight()
                        . fillMaxWidth(if (total > 0) completed.toFloat() / total else 0f)
                        .clip(RoundedCornerShape(4.dp))
                        .background(Color.White)
                )
            }
        }
    }
}

@Composable
private fun BonusRewardCard(current: Int, target: Int, claimedCount: Int) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp))
            .background(GameColors.CardBackground)
            .border(
                width = 1.dp,
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        GameColors. AccentPurple. copy(alpha = 0.5f),
                        GameColors. AccentPink.copy(alpha = 0.5f)
                    )
                ),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(12.dp))
                    . background(GameColors.AccentYellow.copy(alpha = 0.15f)),
                contentAlignment = Alignment. Center
            ) {
                Icon(
                    imageVector = Icons.Default.CardGiftcard,
                    contentDescription = null,
                    tint = GameColors.AccentYellow,
                    modifier = Modifier.size(28.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "额外奖励",
                        color = GameColors.TextPrimary,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier. width(8.dp))
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(GameColors.AccentOrange)
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "$current/$target",
                            color = Color.White,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    if (claimedCount > 0) {
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "已领${claimedCount}次",
                            color = GameColors.AccentGreen,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "每完成5个任务，额外获得 ",
                        color = GameColors. TextSecondary,
                        fontSize = 12.sp
                    )
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = GameColors.AccentYellow,
                        modifier = Modifier.size(14.dp)
                    )
                    Text(
                        text = "300星星",
                        color = GameColors.AccentYellow,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .clip(RoundedCornerShape(3.dp))
                        .background(GameColors. ProgressBackground)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(current.toFloat() / target)
                            .clip(RoundedCornerShape(3.dp))
                            .background(GameColors.AccentYellow)
                    )
                }
            }
        }
    }
}

@Composable
private fun FilterTabs(
    selectedFilter:  String,
    onFilterSelected:  (String) -> Unit
) {
    Row(
        modifier = Modifier. fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        FilterTab(
            text = "全部",
            icon = null,
            isSelected = selectedFilter == "全部",
            selectedColor = GameColors.AccentPurple,
            onClick = { onFilterSelected("全部") }
        )
        FilterTab(
            text = "体质",
            icon = Icons. Outlined.FavoriteBorder,
            isSelected = selectedFilter == "体质",
            selectedColor = GameColors. AttrPhysical,
            onClick = { onFilterSelected("体质") }
        )
        FilterTab(
            text = "修养",
            icon = Icons. Outlined.Spa,
            isSelected = selectedFilter == "修养",
            selectedColor = GameColors.AccentOrange,
            onClick = { onFilterSelected("修养") }
        )
        FilterTab(
            text = "智力",
            icon = Icons.Outlined.Psychology,
            isSelected = selectedFilter == "智力",
            selectedColor = GameColors.AccentBlue,
            onClick = { onFilterSelected("智力") }
        )
    }
}

@Composable
private fun FilterTab(
    text: String,
    icon: ImageVector?,
    isSelected: Boolean,
    selectedColor: Color,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(
                if (isSelected) selectedColor
                else GameColors.CardBackground
            )
            .border(
                width = 1.dp,
                color = if (isSelected) selectedColor else GameColors.BorderLight,
                shape = RoundedCornerShape(20.dp)
            )
            .clickable { onClick() }
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = if (isSelected) Color.White else GameColors.TextSecondary,
                    modifier = Modifier. size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
            }
            Text(
                text = text,
                color = if (isSelected) Color.White else GameColors.TextSecondary,
                fontSize = 13.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
            )
        }
    }
}

@Composable
private fun TaskSectionHeader(title: String, count: Int) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .width(4.dp)
                .height(18.dp)
                .clip(RoundedCornerShape(2.dp))
                .background(GameColors.AccentPurple)
        )
        Spacer(modifier = Modifier. width(8.dp))
        Text(
            text = "$title ($count)",
            color = GameColors.TextPrimary,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun TaskCard(
    task: TaskData,
    onClick: () -> Unit
) {
    val categoryColor = getCategoryColor(task. category)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(2.dp, RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp))
            .background(GameColors.CardBackground)
            .then(
                if (! task.isCompleted) {
                    Modifier.border(
                        width = 2.dp,
                        color = categoryColor. copy(alpha = 0.5f),
                        shape = RoundedCornerShape(16.dp)
                    )
                } else {
                    Modifier
                }
            )
            .clickable { onClick() }
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.Top) {
            Box(
                modifier = Modifier
                    . size(24.dp)
                    .clip(CircleShape)
                    .background(
                        if (task.isCompleted) GameColors.AccentGreen
                        else Color. Transparent
                    )
                    .border(
                        width = 2.dp,
                        color = if (task.isCompleted) GameColors.AccentGreen
                        else categoryColor,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment. Center
            ) {
                if (task.isCompleted) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = task. title,
                    color = if (task.isCompleted) GameColors.TextMuted else GameColors.TextPrimary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    textDecoration = if (task.isCompleted) TextDecoration.LineThrough else null
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = task.description,
                    color = GameColors.TextSecondary,
                    fontSize = 13.sp
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement. spacedBy(12.dp)
                ) {
                    Row(verticalAlignment = Alignment. CenterVertically) {
                        Icon(
                            imageVector = Icons.Outlined.Schedule,
                            contentDescription = null,
                            tint = GameColors.TextMuted,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(
                            text = task.duration,
                            color = GameColors.TextMuted,
                            fontSize = 12.sp
                        )
                    }

                    Row(verticalAlignment = Alignment. CenterVertically) {
                        Icon(
                            imageVector = Icons.Outlined.EmojiEvents,
                            contentDescription = null,
                            tint = GameColors.AccentOrange,
                            modifier = Modifier.size(14.dp)
                        )
                        Text(
                            text = "+${task.expReward}",
                            color = GameColors.AccentOrange,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = GameColors.AccentYellow,
                            modifier = Modifier.size(14.dp)
                        )
                        Text(
                            text = "+${task.starReward}",
                            color = GameColors.AccentYellow,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = when (task.category) {
                                "体质" -> Icons. Outlined.FavoriteBorder
                                "智力" -> Icons.Outlined. Psychology
                                else -> Icons.Outlined. Spa
                            },
                            contentDescription = null,
                            tint = categoryColor,
                            modifier = Modifier.size(14.dp)
                        )
                        Text(
                            text = "+${task.attrValue}",
                            color = categoryColor,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .background(
                        when {
                            task.isCompleted -> GameColors.AccentGreen
                            task.difficulty == "困难" -> GameColors.AccentPurple
                            task.difficulty == "中等" -> GameColors.AccentOrange
                            else -> GameColors.AccentGreen
                        }
                    )
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = if (task.isCompleted) "完成" else task.difficulty,
                    color = Color.White,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}