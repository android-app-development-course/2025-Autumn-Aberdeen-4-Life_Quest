package com.example.gameui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.gameui.model.ScheduleCategory
import com.example.gameui.model.ScheduleItem
import com.example.gameui.ui.theme.GameColors

@Composable
fun ScheduleItemDialog(
    initialItem: ScheduleItem?,
    onDismiss:   () -> Unit,
    onConfirm:   (ScheduleItem) -> Unit
) {
    var title by remember { mutableStateOf(initialItem?.title ?: "") }
    var desc by remember { mutableStateOf(initialItem?.desc ?: "") }
    var day by remember { mutableIntStateOf(initialItem?.dayOfWeek ?: 1) }
    var startTime by remember { mutableStateOf(initialItem?.startTime ?: "09:00") }
    var endTime by remember { mutableStateOf(initialItem?.endTime ?: "10:00") }
    var category by remember { mutableStateOf(initialItem?.category ?: ScheduleCategory.OTHER) }

    val weekDays = listOf("周日", "周一", "周二", "周三", "周四", "周五", "周六")

    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(GameColors.CardBackground)
                .padding(20.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {
                // 标题
                Text(
                    text = if (initialItem == null) "添加日程" else "编辑日程",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = GameColors.TextPrimary
                )

                Spacer(modifier = Modifier.height(20.dp))

                // 标题输入
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("标题") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = GameColors.AccentPurple,
                        unfocusedBorderColor = GameColors. BorderLight
                    ),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(12.dp))

                // 描述输入
                OutlinedTextField(
                    value = desc,
                    onValueChange = { desc = it },
                    label = { Text("描述") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults. colors(
                        focusedBorderColor = GameColors.AccentPurple,
                        unfocusedBorderColor = GameColors.BorderLight
                    ),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                // 星期选择
                Text(
                    text = "选择星期",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = GameColors.TextPrimary
                )

                Spacer(modifier = Modifier. height(8.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    weekDays.forEachIndexed { index, dayName ->
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                . background(
                                    if (day == index) GameColors.AccentPurple
                                    else GameColors.BackgroundGray
                                )
                                .clickable { day = index }
                                .padding(horizontal = 12.dp, vertical = 8.dp)
                        ) {
                            Text(
                                text = dayName,
                                color = if (day == index) Color.White else GameColors.TextSecondary,
                                fontSize = 13.sp,
                                fontWeight = if (day == index) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // 时间选择
                Text(
                    text = "选择时间",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = GameColors.TextPrimary
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier. fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedTextField(
                        value = startTime,
                        onValueChange = { startTime = it },
                        label = { Text("开始") },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = GameColors.AccentPurple,
                            unfocusedBorderColor = GameColors.BorderLight
                        ),
                        singleLine = true,
                        placeholder = { Text("09:00") }
                    )

                    OutlinedTextField(
                        value = endTime,
                        onValueChange = { endTime = it },
                        label = { Text("结束") },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults. colors(
                            focusedBorderColor = GameColors.AccentPurple,
                            unfocusedBorderColor = GameColors.BorderLight
                        ),
                        singleLine = true,
                        placeholder = { Text("10:00") }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // 分类选择
                Text(
                    text = "选择分类",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = GameColors.TextPrimary
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    ScheduleCategory.entries.forEach { cat ->
                        val catColor = when (cat) {
                            ScheduleCategory.PHYSIQUE -> GameColors.AttrPhysical
                            ScheduleCategory. CULTIVATION -> GameColors.AccentOrange
                            ScheduleCategory.INTELLIGENCE -> GameColors.AccentBlue
                            ScheduleCategory.OTHER -> GameColors.TextMuted
                        }

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                . background(
                                    if (category == cat) catColor
                                    else catColor.copy(alpha = 0.15f)
                                )
                                .clickable { category = cat }
                                .padding(horizontal = 12.dp, vertical = 8.dp)
                        ) {
                            Text(
                                text = cat.displayName,
                                color = if (category == cat) Color.White else catColor,
                                fontSize = 13.sp,
                                fontWeight = if (category == cat) FontWeight.Bold else FontWeight. Normal
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // 按钮
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text(
                            text = "取消",
                            color = GameColors.TextSecondary
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = {
                            if (title.isNotBlank()) {
                                onConfirm(
                                    ScheduleItem(
                                        id = initialItem?.id ?: System.currentTimeMillis(),
                                        title = title,
                                        desc = desc,
                                        dayOfWeek = day,
                                        startTime = startTime,
                                        endTime = endTime,
                                        category = category
                                    )
                                )
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = GameColors.AccentPurple
                        ),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("确定")
                    }
                }
            }
        }
    }
}