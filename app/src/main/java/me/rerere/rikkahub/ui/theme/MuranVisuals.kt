package me.rerere.rikkahub.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

/**
 * Muran 暮暗视觉系统（design-system §02-05）
 * 仅当主题 id == "muran" 时生效，其他主题保持原样
 */
object MuranVisuals {
    const val THEME_ID = "muran"

    // 页面暮色渐变（Twilight）
    val twilightBackground = Brush.verticalGradient(
        listOf(Color(0xFF252A3D), Color(0xFF39364D), Color(0xFF574A55))
    )

    // 用户气泡：暖金渐变 #D8B98A → #C8A577
    val userBubble = Brush.horizontalGradient(
        listOf(Color(0xFFD8B98A), Color(0xFFC8A577))
    )
    val userBubbleContent = Color(0xFF181D2B)

    // AI 气泡：暗色玻璃
    val aiBubble = Brush.verticalGradient(
        listOf(Color.White.copy(alpha = 0.07f), Color.White.copy(alpha = 0.11f))
    )
}
