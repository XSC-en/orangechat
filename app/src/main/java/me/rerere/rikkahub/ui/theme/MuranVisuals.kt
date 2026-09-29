package me.rerere.rikkahub.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

object MuranVisuals {
    const val THEME_ID = "muran"

    // 页面背景：暗夜蓝紫渐变
    val twilightBackground = Brush.verticalGradient(
        listOf(Color(0xFF202536), Color(0xFF1A1E2E), Color(0xFF151823))
    )

    // 用户气泡：暮光金渐变（玻璃磨砂）
    val userBubble = Brush.horizontalGradient(
        listOf(Color(0xFFD8B98A), Color(0xFFC4A265))
    )
    val userBubbleContent = Color(0xFF1A1E2E)
    val userBubbleBorder = Color(0xFFD8B98A)

    // AI 气泡：暮紫半透明（玻璃磨砂）
    val aiBubble = Brush.verticalGradient(
        listOf(Color(0x33A89BCB), Color(0x1A1E2E))
    )
    val aiBubbleContent = Color(0xFFE8E4F0)
    val aiBubbleBorder = Color(0xFFA89BCB)
}
