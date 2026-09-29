package me.rerere.rikkahub.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor

object MuranVisuals {
    const val THEME_ID = "muran"

    // 页面温暖纸张渐变
    val twilightBackground = Brush.verticalGradient(
        listOf(Color(0xFFFAF6F0), Color(0xFFF5EFE6), Color(0xFFEDE4D8))
    )

    // 用户气泡：温暖琥珀渐变（玻璃磨砂质感：半透明暖色 + 内部光泽）
    val userBubble = Brush.horizontalGradient(
        listOf(Color(0xFFE8C9A0), Color(0xFFD4AD7A))
    )
    val userBubbleContent = Color(0xFF3A2A14)
    val userBubbleBorder = SolidColor(Color(0xFFD4AD7A))

    // AI 气泡：浅米色卡片（玻璃磨砂：半透明白底 + 暖边）
    val aiBubble = Brush.verticalGradient(
        listOf(Color(0xFFFFFFFF), Color(0xFFF9F3EA))
    )
    val aiBubbleContent = Color(0xFF2C2218)
    val aiBubbleBorder = SolidColor(Color(0xFFE8D5C8))
}
