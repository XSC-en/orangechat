package me.rerere.rikkahub.ui.theme.presets

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val MuranColorScheme = darkColorScheme(
    primary = WarmGoldMain,
    onPrimary = BackgroundDeep,
    primaryContainer = WarmGoldDark,
    secondary = TwilightPurpleMain,
    onSecondary = TextPrimary,
    background = BackgroundMain,
    surface = SurfaceLevel1,
    surfaceVariant = SurfaceLevel2,
    onBackground = TextPrimary,
    onSurface = TextPrimary,
    onSurfaceVariant = TextSecondary
)

@Composable
fun MuranTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = MuranColorScheme,
        shapes = MuranShapes,
        content = content
    )
}
