package me.rerere.rikkahub.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import me.rerere.rikkahub.ui.theme.TwilightPurpleMain

@Composable
fun BreathingDots(
    modifier: Modifier = Modifier,
    dotSize: Dp = 8.dp,
    durationMillis: Int = 1400
) {
    val infiniteTransition = rememberInfiniteTransition(label = "breathing")

    @Composable
    fun animateDot(delay: Int): Float {
        val alpha by infiniteTransition.animateFloat(
            initialValue = 0.2f,
            targetValue = 1.0f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = durationMillis, delayMillis = delay, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "dotAlpha"
        )
        return alpha
    }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val delays = listOf(0, 300, 600)
        delays.forEach { delay ->
            val alpha = animateDot(delay)
            Box(
                modifier = Modifier
                    .size(dotSize)
                    .alpha(alpha)
                    .background(TwilightPurpleMain, CircleShape)
            )
        }
    }
}
