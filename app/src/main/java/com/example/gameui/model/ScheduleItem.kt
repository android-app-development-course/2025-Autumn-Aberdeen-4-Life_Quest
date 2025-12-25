package com.example.gameui.model

import androidx.compose.ui.graphics.Color

enum class ScheduleCategory(val displayName: String, val color: Color) {
    PHYSIQUE("体质", Color(0xFFE57373)),      // 红
    CULTIVATION("修养", Color(0xFFBA68C8)),   // 紫
    INTELLIGENCE("智力", Color(0xFF64B5F6)),  // 蓝
    OTHER("其他", Color.Black)          // 灰
}

data class ScheduleItem(
    val id: Long = System.currentTimeMillis(),
    val title: String,
    val desc: String,
    val dayOfWeek: Int, // 0-6 (周日-周六)
    val startTime: String,
    val endTime: String,
    val category: ScheduleCategory
)