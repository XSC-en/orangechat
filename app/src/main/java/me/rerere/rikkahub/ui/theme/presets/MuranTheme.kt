/*
 * 暮暗 Muran - 暮色主题
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

//region Muran Light Colors
private val primaryLight = Color(0xFFB99668)
private val onPrimaryLight = Color(0xFF181D2B)
private val primaryContainerLight = Color(0xFFE7CFA8)
private val onPrimaryContainerLight = Color(0xFF3E2E1A)
private val secondaryLight = Color(0xFF71658E)
private val onSecondaryLight = Color(0xFFF0EDF1)
private val secondaryContainerLight = Color(0xFFC0B6DD)
private val onSecondaryContainerLight = Color(0xFF2A2538)
private val backgroundLight = Color(0xFFF0EDF1)
private val onBackgroundLight = Color(0xFF202536)
private val surfaceLight = Color(0xFFF7F5F8)
private val onSurfaceLight = Color(0xFF202536)
private val surfaceVariantLight = Color(0xFFE8E4EC)
private val onSurfaceVariantLight = Color(0xFF4A4655)
private val outlineLight = Color(0xFF7A7688)
private val outlineVariantLight = Color(0xFFCEC8D6)
private val scrimLight = Color(0xFF000000)
private val inverseSurfaceLight = Color(0xFF353340)
private val inverseOnSurfaceLight = Color(0xFFF0EDF1)
private val inversePrimaryLight = Color(0xFFD8B98A)
private val surfaceDimLight = Color(0xFFDED9E0)
private val surfaceBrightLight = Color(0xFFF7F5F8)
private val surfaceContainerLowestLight = Color(0xFFFFFFFF)
private val surfaceContainerLowLight = Color(0xFFF2EFF4)
private val surfaceContainerLight = Color(0xFFEAE6ED)
private val surfaceContainerHighLight = Color(0xFFE4E0E8)
private val surfaceContainerHighestLight = Color(0xFFDEDAE2)
//endregion

//region Muran Dark Colors
private val primaryDark = Color(0xFFD8B98A)
private val onPrimaryDark = Color(0xFF181D2B)
private val primaryContainerDark = Color(0xFFB99668)
private val onPrimaryContainerDark = Color(0xFF181D2B)
private val secondaryDark = Color(0xFFA89BCB)
private val onSecondaryDark = Color(0xFF202536)
private val secondaryContainerDark = Color(0xFF71658E)
private val onSecondaryContainerDark = Color(0xFFF0EDF1)
private val backgroundDark = Color(0xFF202536)
private val onBackgroundDark = Color(0xFFF0EDF1)
private val surfaceDark = Color(0xFF303548)
private val onSurfaceDark = Color(0xFFF0EDF1)
private val surfaceVariantDark = Color(0xFF383B50)
private val onSurfaceVariantDark = Color(0xFFC2C0C8)
private val outlineDark = Color(0xFF9695A2)
private val outlineVariantDark = Color(0xFF4A4858)
private val scrimDark = Color(0xFF000000)
private val inverseSurfaceDark = Color(0xFFE7E0EC)
private val inverseOnSurfaceDark = Color(0xFF202536)
private val inversePrimaryDark = Color(0xFFB99668)
private val surfaceDimDark = Color(0xFF202536)
private val surfaceBrightDark = Color(0xFF363848)
private val surfaceContainerLowestDark = Color(0xFF181D2B)
private val surfaceContainerLowDark = Color(0xFF252A3D)
private val surfaceContainerDark = Color(0xFF292D40)
private val surfaceContainerHighDark = Color(0xFF2F3245)
private val surfaceContainerHighestDark = Color(0xFF393D52)
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
