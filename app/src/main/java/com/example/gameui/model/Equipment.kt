package com.example.gameui.model

import androidx.compose.ui.graphics.Color

data class Equipment(
    val id: Int,
    val name: String,
    val description: String,
    val price: Int,
    val type: String, // "physique" | "cultivation" | "intelligence"
    val bonus: Int
) {
    // 方便 UI 根据类型选颜色（可自定义）
    fun colorForType(): Color {
        return when (type) {
            "physique" -> Color(0xFFEF4444) // red-ish
            "cultivation" -> Color(0xFF8b5cf6) // purple
            "intelligence" -> Color(0xFF3b82f6) // blue
            else -> Color.Companion.Black
        }
    }
}