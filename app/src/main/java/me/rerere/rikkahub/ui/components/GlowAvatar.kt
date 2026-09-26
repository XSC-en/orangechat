package me.rerere.rikkahub.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import me.rerere.rikkahub.ui.theme.WarmGoldLight
import me.rerere.rikkahub.ui.theme.WarmGoldMain

@Composable
fun GlowAvatar(
    modifier: Modifier = Modifier,
    size: Dp = 48.dp,
    content: @Composable () -> Unit
) {
    val glowBrush = Brush.radialGradient(
        colors = listOf(
            WarmGoldLight.copy(alpha = 0.6f),
            WarmGoldMain.copy(alpha = 0.2f),
            Color.Transparent
        )
    )

    Box(
        modifier = modifier
            .size(size)
            .border(1.5.dp, glowBrush, CircleShape)
            .padding(3.dp)
            .clip(CircleShape)
    ) {
        content()
    }
}
