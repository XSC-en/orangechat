/*
 * 暮暗 Muran → 手帐日记风
 * 深夜台灯下的温暖纸张质感
 */

package me.rerere.rikkahub.ui.theme.presets

import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import me.rerere.rikkahub.R
import me.rerere.rikkahub.ui.theme.PresetTheme

val MuranThemePreset by lazy {
    PresetTheme(
        id = "muran",
        name = {
            Text(stringResource(id = R.string.theme_name_muran))
        },
        standardLight = lightScheme,
        standardDark = darkScheme,
    )
}

//region 手帐日记风 Light Colors
private val primaryLight = Color(0xFFB07D4F)
private val onPrimaryLight = Color(0xFFFFFFFF)
private val primaryContainerLight = Color(0xFFF0D9BE)
private val onPrimaryContainerLight = Color(0xFF3A2A14)
private val secondaryLight = Color(0xFF8B6B5A)
private val onSecondaryLight = Color(0xFFFFFFFF)
private val secondaryContainerLight = Color(0xFFE8D5C8)
private val onSecondaryContainerLight = Color(0xFF3A2A20)
private val backgroundLight = Color(0xFFFAF6F0)
private val onBackgroundLight = Color(0xFF2C2218)
private val surfaceLight = Color(0xFFFFFBF5)
private val onSurfaceLight = Color(0xFF2C2218)
private val surfaceVariantLight = Color(0xFFF0E6DA)
private val onSurfaceVariantLight = Color(0xFF5A4C3E)
private val outlineLight = Color(0xFF8C7E6E)
private val outlineVariantLight = Color(0xFFDDD0C2)
private val scrimLight = Color(0xFF000000)
private val inverseSurfaceLight = Color(0xFF3A2E22)
private val inverseOnSurfaceLight = Color(0xFFFAF0E4)
private val inversePrimaryLight = Color(0xFFE8C9A0)
private val surfaceDimLight = Color(0xFFE8DED2)
private val surfaceBrightLight = Color(0xFFFFFBF5)
private val surfaceContainerLowestLight = Color(0xFFFFFFFF)
private val surfaceContainerLowLight = Color(0xFFF5EFE6)
private val surfaceContainerLight = Color(0xFFF0E8DC)
private val surfaceContainerHighLight = Color(0xFFEAE0D4)
private val surfaceContainerHighestLight = Color(0xFFE4DAD0)
//endregion

//region 手帐日记风 Dark Colors（暖夜色）
private val primaryDark = Color(0xFFE8C9A0)
private val onPrimaryDark = Color(0xFF2C2218)
private val primaryContainerDark = Color(0xFF8A6540)
private val onPrimaryContainerDark = Color(0xFFFAF0E4)
private val secondaryDark = Color(0xFFD4B8A0)
private val onSecondaryDark = Color(0xFF2C2218)
private val secondaryContainerDark = Color(0xFF6A5240)
private val onSecondaryContainerDark = Color(0xFFF0E0D0)
private val backgroundDark = Color(0xFF2A221A)
private val onBackgroundDark = Color(0xFFF0E4D6)
private val surfaceDark = Color(0xFF383028)
private val onSurfaceDark = Color(0xFFF0E4D6)
private val surfaceVariantDark = Color(0xFF4A3E32)
private val onSurfaceVariantDark = Color(0xFFD0C0AE)
private val outlineDark = Color(0xFFA09080)
private val outlineVariantDark = Color(0xFF4A3E32)
private val scrimDark = Color(0xFF000000)
private val inverseSurfaceDark = Color(0xFFEDE4D8)
private val inverseOnSurfaceDark = Color(0xFF2C2218)
private val inversePrimaryDark = Color(0xFFB07D4F)
private val surfaceDimDark = Color(0xFF2A221A)
private val surfaceBrightDark = Color(0xFF483E34)
private val surfaceContainerLowestDark = Color(0xFF221C14)
private val surfaceContainerLowDark = Color(0xFF302820)
private val surfaceContainerDark = Color(0xFF362E26)
private val surfaceContainerHighDark = Color(0xFF403630)
private val surfaceContainerHighestDark = Color(0xFF4C4238)
//endregion

private val lightScheme = lightColorScheme(
    primary = primaryLight,
    onPrimary = onPrimaryLight,
    primaryContainer = primaryContainerLight,
    onPrimaryContainer = onPrimaryContainerLight,
    secondary = secondaryLight,
    onSecondary = onSecondaryLight,
    secondaryContainer = secondaryContainerLight,
    onSecondaryContainer = onSecondaryContainerLight,
    background = backgroundLight,
    onBackground = onBackgroundLight,
    surface = surfaceLight,
    onSurface = onSurfaceLight,
    surfaceVariant = surfaceVariantLight,
    onSurfaceVariant = onSurfaceVariantLight,
    outline = outlineLight,
    outlineVariant = outlineVariantLight,
    scrim = scrimLight,
    inverseSurface = inverseSurfaceLight,
    inverseOnSurface = inverseOnSurfaceLight,
    inversePrimary = inversePrimaryLight,
    surfaceDim = surfaceDimLight,
    surfaceBright = surfaceBrightLight,
    surfaceContainerLowest = surfaceContainerLowestLight,
    surfaceContainerLow = surfaceContainerLowLight,
    surfaceContainer = surfaceContainerLight,
    surfaceContainerHigh = surfaceContainerHighLight,
    surfaceContainerHighest = surfaceContainerHighestLight,
)

private val darkScheme = darkColorScheme(
    primary = primaryDark,
    onPrimary = onPrimaryDark,
    primaryContainer = primaryContainerDark,
    onPrimaryContainer = onPrimaryContainerDark,
    secondary = secondaryDark,
    onSecondary = onSecondaryDark,
    secondaryContainer = secondaryContainerDark,
    onSecondaryContainer = onSecondaryContainerDark,
    background = backgroundDark,
    onBackground = onBackgroundDark,
    surface = surfaceDark,
    onSurface = onSurfaceDark,
    surfaceVariant = surfaceVariantDark,
    onSurfaceVariant = onSurfaceVariantDark,
    outline = outlineDark,
    outlineVariant = outlineVariantDark,
    scrim = scrimDark,
    inverseSurface = inverseSurfaceDark,
    inverseOnSurface = inverseOnSurfaceDark,
    inversePrimary = inversePrimaryDark,
    surfaceDim = surfaceDimDark,
    surfaceBright = surfaceBrightDark,
    surfaceContainerLowest = surfaceContainerLowestDark,
    surfaceContainerLow = surfaceContainerLowDark,
    surfaceContainer = surfaceContainerDark,
    surfaceContainerHigh = surfaceContainerHighDark,
    surfaceContainerHighest = surfaceContainerHighestDark,
)
