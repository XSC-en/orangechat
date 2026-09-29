package me.rerere.rikkahub.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

/**
 * Muran 暮暗视觉系统 → 手帐日记风
 * 仅当主题 id == "muran" 时生效，其他主题保持原样
 */
object MuranVisuals {
    const val THEME_ID = "muran"

    // 页面温暖纸张渐变（深夜台灯下的手帐）
    val twilightBackground = Brush.verticalGradient(
        listOf(Color(0xFFFAF6F0), Color(0xFFF5EFE6), Color(0xFFEDE4D8))
    )

    // 用户气泡：温暖琥珀渐变
    val userBubble = Brush.horizontalGradient(
        listOf(Color(0xFFE8C9A0), Color(0xFFD4AD7A))
    )
    val userBubbleContent = Color(0xFF3A2A14)

    // AI 气泡：浅米色卡片
    val aiBubble = Brush.verticalGradient(
        listOf(Color(0xFFFFFFFF), Color(0xFFF9F3EA))
    )
}
