/*
 * 橘瓣 OrangeChat
 * 衍生自 RikkaHub (https://github.com/rikkahub/rikkahub)，原作者 RE
 * 本项目基于 GNU AGPL v3 开源，详见根目录 LICENSE 文件
 */

package me.rerere.rikkahub.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontWeight
import me.rerere.rikkahub.R

// Set of Material typography styles to start with
val Typography = Typography()

// Muran 暮色主题专用字体配置
val MuranTypography = Typography(
    displayLarge = Typography().displayLarge.copy(
        fontSize = androidx.compose.ui.unit.sp.Value(32),
        fontWeight = FontWeight.Bold
    ),
    titleLarge = Typography().titleLarge.copy(
        fontSize = androidx.compose.ui.unit.sp.Value(20),
        fontWeight = FontWeight.Medium
    ),
    bodyLarge = Typography().bodyLarge.copy(
        fontSize = androidx.compose.ui.unit.sp.Value(16)
    ),
    bodyMedium = Typography().bodyMedium.copy(
        fontSize = androidx.compose.ui.unit.sp.Value(14)
    ),
    labelSmall = Typography().labelSmall.copy(
        fontSize = androidx.compose.ui.unit.sp.Value(12)
    )
)

@OptIn(ExperimentalTextApi::class)
val JetbrainsMono = FontFamily(
    Font(
        resId = R.font.jetbrains_mono,
        variationSettings = FontVariation.Settings(
            FontVariation.weight(FontWeight.Normal.weight),
        )
    )
)
