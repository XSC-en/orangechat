package me.rerere.rikkahub.ui.theme.presets

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// Background
val BackgroundMain = Color(0xFF202536)
val BackgroundDeep = Color(0xFF181D2B)
val TwilightPurpleGlow = Color(0x266F668B)

// Surface
val SurfaceLevel1 = Color(0xFF303548)
val SurfaceLevel2 = Color(0xFF383B50)
val GlassSurface = Color(0x1AFFFFFF)
val GlassSurfaceLight = Color(0x0FFFFFFF)
val GlassBorder = Color(0x1FFFFFFF)
val GlassHighlight = Color(0x1FFFFFFF)

// Primary Warm Gold
val WarmGoldMain = Color(0xFFD8B98A)
val WarmGoldLight = Color(0xFFE7CFA8)
val WarmGoldDark = Color(0xFFB99668)

// Twilight Purple
val TwilightPurpleMain = Color(0xFFA89BCB)
val TwilightPurpleLight = Color(0xFFC0B6DD)
val TwilightPurpleDark = Color(0xFF71658E)

// Text
val TextPrimary = Color(0xFFF0EDF1)
val TextSecondary = Color(0xFFC2C0C8)
val TextTertiary = Color(0xFF9695A2)
val TextDisabled = Color(0xFF737582)

// Gradients
val TwilightGradient = Brush.linearGradient(
    colors = listOf(Color(0xFF252A3D), Color(0xFF39364D), Color(0xFF574A55))
)
val SunsetGradient = Brush.linearGradient(
    colors = listOf(Color(0xFF596078), Color(0xFF807082), Color(0xFFD1A77C))
)
val SoftPurpleGradient = Brush.linearGradient(
    colors = listOf(Color(0xFF3A3850), Color(0xFF625B78), Color(0xFF8A819E))
)
val BackgroundGradient = Brush.verticalGradient(
    colors = listOf(Color(0xFF202536), Color(0xFF292B43), Color(0xFF34334B))
)
val UserBubbleGradient = Brush.horizontalGradient(
    colors = listOf(WarmGoldMain, Color(0xFFC8A577))
)
