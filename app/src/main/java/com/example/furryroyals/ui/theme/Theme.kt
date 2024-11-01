package com.example.compose
import android.os.Build
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.example.furryroyals.ui.theme.backgroundDark
import com.example.furryroyals.ui.theme.backgroundDarkHighContrast
import com.example.furryroyals.ui.theme.backgroundDarkMediumContrast
import com.example.furryroyals.ui.theme.backgroundLight
import com.example.furryroyals.ui.theme.backgroundLightHighContrast
import com.example.furryroyals.ui.theme.backgroundLightMediumContrast
import com.example.furryroyals.ui.theme.errorContainerDark
import com.example.furryroyals.ui.theme.errorContainerDarkHighContrast
import com.example.furryroyals.ui.theme.errorContainerDarkMediumContrast
import com.example.furryroyals.ui.theme.errorContainerLight
import com.example.furryroyals.ui.theme.errorContainerLightHighContrast
import com.example.furryroyals.ui.theme.errorContainerLightMediumContrast
import com.example.furryroyals.ui.theme.errorDark
import com.example.furryroyals.ui.theme.errorDarkHighContrast
import com.example.furryroyals.ui.theme.errorDarkMediumContrast
import com.example.furryroyals.ui.theme.errorLight
import com.example.furryroyals.ui.theme.errorLightHighContrast
import com.example.furryroyals.ui.theme.errorLightMediumContrast
import com.example.furryroyals.ui.theme.inverseOnSurfaceDark
import com.example.furryroyals.ui.theme.inverseOnSurfaceDarkHighContrast
import com.example.furryroyals.ui.theme.inverseOnSurfaceDarkMediumContrast
import com.example.furryroyals.ui.theme.inverseOnSurfaceLight
import com.example.furryroyals.ui.theme.inverseOnSurfaceLightHighContrast
import com.example.furryroyals.ui.theme.inverseOnSurfaceLightMediumContrast
import com.example.furryroyals.ui.theme.inversePrimaryDark
import com.example.furryroyals.ui.theme.inversePrimaryDarkHighContrast
import com.example.furryroyals.ui.theme.inversePrimaryDarkMediumContrast
import com.example.furryroyals.ui.theme.inversePrimaryLight
import com.example.furryroyals.ui.theme.inversePrimaryLightHighContrast
import com.example.furryroyals.ui.theme.inversePrimaryLightMediumContrast
import com.example.furryroyals.ui.theme.inverseSurfaceDark
import com.example.furryroyals.ui.theme.inverseSurfaceDarkHighContrast
import com.example.furryroyals.ui.theme.inverseSurfaceDarkMediumContrast
import com.example.furryroyals.ui.theme.inverseSurfaceLight
import com.example.furryroyals.ui.theme.inverseSurfaceLightHighContrast
import com.example.furryroyals.ui.theme.inverseSurfaceLightMediumContrast
import com.example.furryroyals.ui.theme.onBackgroundDark
import com.example.furryroyals.ui.theme.onBackgroundDarkHighContrast
import com.example.furryroyals.ui.theme.onBackgroundDarkMediumContrast
import com.example.furryroyals.ui.theme.onBackgroundLight
import com.example.furryroyals.ui.theme.onBackgroundLightHighContrast
import com.example.furryroyals.ui.theme.onBackgroundLightMediumContrast
import com.example.furryroyals.ui.theme.onErrorContainerDark
import com.example.furryroyals.ui.theme.onErrorContainerDarkHighContrast
import com.example.furryroyals.ui.theme.onErrorContainerDarkMediumContrast
import com.example.furryroyals.ui.theme.onErrorContainerLight
import com.example.furryroyals.ui.theme.onErrorContainerLightHighContrast
import com.example.furryroyals.ui.theme.onErrorContainerLightMediumContrast
import com.example.furryroyals.ui.theme.onErrorDark
import com.example.furryroyals.ui.theme.onErrorDarkHighContrast
import com.example.furryroyals.ui.theme.onErrorDarkMediumContrast
import com.example.furryroyals.ui.theme.onErrorLight
import com.example.furryroyals.ui.theme.onErrorLightHighContrast
import com.example.furryroyals.ui.theme.onErrorLightMediumContrast
import com.example.furryroyals.ui.theme.onPrimaryContainerDark
import com.example.furryroyals.ui.theme.onPrimaryContainerDarkHighContrast
import com.example.furryroyals.ui.theme.onPrimaryContainerDarkMediumContrast
import com.example.furryroyals.ui.theme.onPrimaryContainerLight
import com.example.furryroyals.ui.theme.onPrimaryContainerLightHighContrast
import com.example.furryroyals.ui.theme.onPrimaryContainerLightMediumContrast
import com.example.furryroyals.ui.theme.onPrimaryDark
import com.example.furryroyals.ui.theme.onPrimaryDarkHighContrast
import com.example.furryroyals.ui.theme.onPrimaryDarkMediumContrast
import com.example.furryroyals.ui.theme.onPrimaryLight
import com.example.furryroyals.ui.theme.onPrimaryLightHighContrast
import com.example.furryroyals.ui.theme.onPrimaryLightMediumContrast
import com.example.furryroyals.ui.theme.onSecondaryContainerDark
import com.example.furryroyals.ui.theme.onSecondaryContainerDarkHighContrast
import com.example.furryroyals.ui.theme.onSecondaryContainerDarkMediumContrast
import com.example.furryroyals.ui.theme.onSecondaryContainerLight
import com.example.furryroyals.ui.theme.onSecondaryContainerLightHighContrast
import com.example.furryroyals.ui.theme.onSecondaryContainerLightMediumContrast
import com.example.furryroyals.ui.theme.onSecondaryDark
import com.example.furryroyals.ui.theme.onSecondaryDarkHighContrast
import com.example.furryroyals.ui.theme.onSecondaryDarkMediumContrast
import com.example.furryroyals.ui.theme.onSecondaryLight
import com.example.furryroyals.ui.theme.onSecondaryLightHighContrast
import com.example.furryroyals.ui.theme.onSecondaryLightMediumContrast
import com.example.furryroyals.ui.theme.onSurfaceDark
import com.example.furryroyals.ui.theme.onSurfaceDarkHighContrast
import com.example.furryroyals.ui.theme.onSurfaceDarkMediumContrast
import com.example.furryroyals.ui.theme.onSurfaceLight
import com.example.furryroyals.ui.theme.onSurfaceLightHighContrast
import com.example.furryroyals.ui.theme.onSurfaceLightMediumContrast
import com.example.furryroyals.ui.theme.onSurfaceVariantDark
import com.example.furryroyals.ui.theme.onSurfaceVariantDarkHighContrast
import com.example.furryroyals.ui.theme.onSurfaceVariantDarkMediumContrast
import com.example.furryroyals.ui.theme.onSurfaceVariantLight
import com.example.furryroyals.ui.theme.onSurfaceVariantLightHighContrast
import com.example.furryroyals.ui.theme.onSurfaceVariantLightMediumContrast
import com.example.furryroyals.ui.theme.onTertiaryContainerDark
import com.example.furryroyals.ui.theme.onTertiaryContainerDarkHighContrast
import com.example.furryroyals.ui.theme.onTertiaryContainerDarkMediumContrast
import com.example.furryroyals.ui.theme.onTertiaryContainerLight
import com.example.furryroyals.ui.theme.onTertiaryContainerLightHighContrast
import com.example.furryroyals.ui.theme.onTertiaryContainerLightMediumContrast
import com.example.furryroyals.ui.theme.onTertiaryDark
import com.example.furryroyals.ui.theme.onTertiaryDarkHighContrast
import com.example.furryroyals.ui.theme.onTertiaryDarkMediumContrast
import com.example.furryroyals.ui.theme.onTertiaryLight
import com.example.furryroyals.ui.theme.onTertiaryLightHighContrast
import com.example.furryroyals.ui.theme.onTertiaryLightMediumContrast
import com.example.furryroyals.ui.theme.outlineDark
import com.example.furryroyals.ui.theme.outlineDarkHighContrast
import com.example.furryroyals.ui.theme.outlineDarkMediumContrast
import com.example.furryroyals.ui.theme.outlineLight
import com.example.furryroyals.ui.theme.outlineLightHighContrast
import com.example.furryroyals.ui.theme.outlineLightMediumContrast
import com.example.furryroyals.ui.theme.outlineVariantDark
import com.example.furryroyals.ui.theme.outlineVariantDarkHighContrast
import com.example.furryroyals.ui.theme.outlineVariantDarkMediumContrast
import com.example.furryroyals.ui.theme.outlineVariantLight
import com.example.furryroyals.ui.theme.outlineVariantLightHighContrast
import com.example.furryroyals.ui.theme.outlineVariantLightMediumContrast
import com.example.furryroyals.ui.theme.primaryContainerDark
import com.example.furryroyals.ui.theme.primaryContainerDarkHighContrast
import com.example.furryroyals.ui.theme.primaryContainerDarkMediumContrast
import com.example.furryroyals.ui.theme.primaryContainerLight
import com.example.furryroyals.ui.theme.primaryContainerLightHighContrast
import com.example.furryroyals.ui.theme.primaryContainerLightMediumContrast
import com.example.furryroyals.ui.theme.primaryDark
import com.example.furryroyals.ui.theme.primaryDarkHighContrast
import com.example.furryroyals.ui.theme.primaryDarkMediumContrast
import com.example.furryroyals.ui.theme.primaryLight
import com.example.furryroyals.ui.theme.primaryLightHighContrast
import com.example.furryroyals.ui.theme.primaryLightMediumContrast
import com.example.furryroyals.ui.theme.scrimDark
import com.example.furryroyals.ui.theme.scrimDarkHighContrast
import com.example.furryroyals.ui.theme.scrimDarkMediumContrast
import com.example.furryroyals.ui.theme.scrimLight
import com.example.furryroyals.ui.theme.scrimLightHighContrast
import com.example.furryroyals.ui.theme.scrimLightMediumContrast
import com.example.furryroyals.ui.theme.secondaryContainerDark
import com.example.furryroyals.ui.theme.secondaryContainerDarkHighContrast
import com.example.furryroyals.ui.theme.secondaryContainerDarkMediumContrast
import com.example.furryroyals.ui.theme.secondaryContainerLight
import com.example.furryroyals.ui.theme.secondaryContainerLightHighContrast
import com.example.furryroyals.ui.theme.secondaryContainerLightMediumContrast
import com.example.furryroyals.ui.theme.secondaryDark
import com.example.furryroyals.ui.theme.secondaryDarkHighContrast
import com.example.furryroyals.ui.theme.secondaryDarkMediumContrast
import com.example.furryroyals.ui.theme.secondaryLight
import com.example.furryroyals.ui.theme.secondaryLightHighContrast
import com.example.furryroyals.ui.theme.secondaryLightMediumContrast
import com.example.furryroyals.ui.theme.surfaceBrightDark
import com.example.furryroyals.ui.theme.surfaceBrightDarkHighContrast
import com.example.furryroyals.ui.theme.surfaceBrightDarkMediumContrast
import com.example.furryroyals.ui.theme.surfaceBrightLight
import com.example.furryroyals.ui.theme.surfaceBrightLightHighContrast
import com.example.furryroyals.ui.theme.surfaceBrightLightMediumContrast
import com.example.furryroyals.ui.theme.surfaceContainerDark
import com.example.furryroyals.ui.theme.surfaceContainerDarkHighContrast
import com.example.furryroyals.ui.theme.surfaceContainerDarkMediumContrast
import com.example.furryroyals.ui.theme.surfaceContainerHighDark
import com.example.furryroyals.ui.theme.surfaceContainerHighDarkHighContrast
import com.example.furryroyals.ui.theme.surfaceContainerHighDarkMediumContrast
import com.example.furryroyals.ui.theme.surfaceContainerHighLight
import com.example.furryroyals.ui.theme.surfaceContainerHighLightHighContrast
import com.example.furryroyals.ui.theme.surfaceContainerHighLightMediumContrast
import com.example.furryroyals.ui.theme.surfaceContainerHighestDark
import com.example.furryroyals.ui.theme.surfaceContainerHighestDarkHighContrast
import com.example.furryroyals.ui.theme.surfaceContainerHighestDarkMediumContrast
import com.example.furryroyals.ui.theme.surfaceContainerHighestLight
import com.example.furryroyals.ui.theme.surfaceContainerHighestLightHighContrast
import com.example.furryroyals.ui.theme.surfaceContainerHighestLightMediumContrast
import com.example.furryroyals.ui.theme.surfaceContainerLight
import com.example.furryroyals.ui.theme.surfaceContainerLightHighContrast
import com.example.furryroyals.ui.theme.surfaceContainerLightMediumContrast
import com.example.furryroyals.ui.theme.surfaceContainerLowDark
import com.example.furryroyals.ui.theme.surfaceContainerLowDarkHighContrast
import com.example.furryroyals.ui.theme.surfaceContainerLowDarkMediumContrast
import com.example.furryroyals.ui.theme.surfaceContainerLowLight
import com.example.furryroyals.ui.theme.surfaceContainerLowLightHighContrast
import com.example.furryroyals.ui.theme.surfaceContainerLowLightMediumContrast
import com.example.furryroyals.ui.theme.surfaceContainerLowestDark
import com.example.furryroyals.ui.theme.surfaceContainerLowestDarkHighContrast
import com.example.furryroyals.ui.theme.surfaceContainerLowestDarkMediumContrast
import com.example.furryroyals.ui.theme.surfaceContainerLowestLight
import com.example.furryroyals.ui.theme.surfaceContainerLowestLightHighContrast
import com.example.furryroyals.ui.theme.surfaceContainerLowestLightMediumContrast
import com.example.furryroyals.ui.theme.surfaceDark
import com.example.furryroyals.ui.theme.surfaceDarkHighContrast
import com.example.furryroyals.ui.theme.surfaceDarkMediumContrast
import com.example.furryroyals.ui.theme.surfaceDimDark
import com.example.furryroyals.ui.theme.surfaceDimDarkHighContrast
import com.example.furryroyals.ui.theme.surfaceDimDarkMediumContrast
import com.example.furryroyals.ui.theme.surfaceDimLight
import com.example.furryroyals.ui.theme.surfaceDimLightHighContrast
import com.example.furryroyals.ui.theme.surfaceDimLightMediumContrast
import com.example.furryroyals.ui.theme.surfaceLight
import com.example.furryroyals.ui.theme.surfaceLightHighContrast
import com.example.furryroyals.ui.theme.surfaceLightMediumContrast
import com.example.furryroyals.ui.theme.surfaceVariantDark
import com.example.furryroyals.ui.theme.surfaceVariantDarkHighContrast
import com.example.furryroyals.ui.theme.surfaceVariantDarkMediumContrast
import com.example.furryroyals.ui.theme.surfaceVariantLight
import com.example.furryroyals.ui.theme.surfaceVariantLightHighContrast
import com.example.furryroyals.ui.theme.surfaceVariantLightMediumContrast
import com.example.furryroyals.ui.theme.tertiaryContainerDark
import com.example.furryroyals.ui.theme.tertiaryContainerDarkHighContrast
import com.example.furryroyals.ui.theme.tertiaryContainerDarkMediumContrast
import com.example.furryroyals.ui.theme.tertiaryContainerLight
import com.example.furryroyals.ui.theme.tertiaryContainerLightHighContrast
import com.example.furryroyals.ui.theme.tertiaryContainerLightMediumContrast
import com.example.furryroyals.ui.theme.tertiaryDark
import com.example.furryroyals.ui.theme.tertiaryDarkHighContrast
import com.example.furryroyals.ui.theme.tertiaryDarkMediumContrast
import com.example.furryroyals.ui.theme.tertiaryLight
import com.example.furryroyals.ui.theme.tertiaryLightHighContrast
import com.example.furryroyals.ui.theme.tertiaryLightMediumContrast

private val lightScheme = lightColorScheme(
    primary = primaryLight,
    onPrimary = onPrimaryLight,
    primaryContainer = primaryContainerLight,
    onPrimaryContainer = onPrimaryContainerLight,
    secondary = secondaryLight,
    onSecondary = onSecondaryLight,
    secondaryContainer = secondaryContainerLight,
    onSecondaryContainer = onSecondaryContainerLight,
    tertiary = tertiaryLight,
    onTertiary = onTertiaryLight,
    tertiaryContainer = tertiaryContainerLight,
    onTertiaryContainer = onTertiaryContainerLight,
    error = errorLight,
    onError = onErrorLight,
    errorContainer = errorContainerLight,
    onErrorContainer = onErrorContainerLight,
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
    tertiary = tertiaryDark,
    onTertiary = onTertiaryDark,
    tertiaryContainer = tertiaryContainerDark,
    onTertiaryContainer = onTertiaryContainerDark,
    error = errorDark,
    onError = onErrorDark,
    errorContainer = errorContainerDark,
    onErrorContainer = onErrorContainerDark,
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

private val mediumContrastLightColorScheme = lightColorScheme(
    primary = primaryLightMediumContrast,
    onPrimary = onPrimaryLightMediumContrast,
    primaryContainer = primaryContainerLightMediumContrast,
    onPrimaryContainer = onPrimaryContainerLightMediumContrast,
    secondary = secondaryLightMediumContrast,
    onSecondary = onSecondaryLightMediumContrast,
    secondaryContainer = secondaryContainerLightMediumContrast,
    onSecondaryContainer = onSecondaryContainerLightMediumContrast,
    tertiary = tertiaryLightMediumContrast,
    onTertiary = onTertiaryLightMediumContrast,
    tertiaryContainer = tertiaryContainerLightMediumContrast,
    onTertiaryContainer = onTertiaryContainerLightMediumContrast,
    error = errorLightMediumContrast,
    onError = onErrorLightMediumContrast,
    errorContainer = errorContainerLightMediumContrast,
    onErrorContainer = onErrorContainerLightMediumContrast,
    background = backgroundLightMediumContrast,
    onBackground = onBackgroundLightMediumContrast,
    surface = surfaceLightMediumContrast,
    onSurface = onSurfaceLightMediumContrast,
    surfaceVariant = surfaceVariantLightMediumContrast,
    onSurfaceVariant = onSurfaceVariantLightMediumContrast,
    outline = outlineLightMediumContrast,
    outlineVariant = outlineVariantLightMediumContrast,
    scrim = scrimLightMediumContrast,
    inverseSurface = inverseSurfaceLightMediumContrast,
    inverseOnSurface = inverseOnSurfaceLightMediumContrast,
    inversePrimary = inversePrimaryLightMediumContrast,
    surfaceDim = surfaceDimLightMediumContrast,
    surfaceBright = surfaceBrightLightMediumContrast,
    surfaceContainerLowest = surfaceContainerLowestLightMediumContrast,
    surfaceContainerLow = surfaceContainerLowLightMediumContrast,
    surfaceContainer = surfaceContainerLightMediumContrast,
    surfaceContainerHigh = surfaceContainerHighLightMediumContrast,
    surfaceContainerHighest = surfaceContainerHighestLightMediumContrast,
)

private val highContrastLightColorScheme = lightColorScheme(
    primary = primaryLightHighContrast,
    onPrimary = onPrimaryLightHighContrast,
    primaryContainer = primaryContainerLightHighContrast,
    onPrimaryContainer = onPrimaryContainerLightHighContrast,
    secondary = secondaryLightHighContrast,
    onSecondary = onSecondaryLightHighContrast,
    secondaryContainer = secondaryContainerLightHighContrast,
    onSecondaryContainer = onSecondaryContainerLightHighContrast,
    tertiary = tertiaryLightHighContrast,
    onTertiary = onTertiaryLightHighContrast,
    tertiaryContainer = tertiaryContainerLightHighContrast,
    onTertiaryContainer = onTertiaryContainerLightHighContrast,
    error = errorLightHighContrast,
    onError = onErrorLightHighContrast,
    errorContainer = errorContainerLightHighContrast,
    onErrorContainer = onErrorContainerLightHighContrast,
    background = backgroundLightHighContrast,
    onBackground = onBackgroundLightHighContrast,
    surface = surfaceLightHighContrast,
    onSurface = onSurfaceLightHighContrast,
    surfaceVariant = surfaceVariantLightHighContrast,
    onSurfaceVariant = onSurfaceVariantLightHighContrast,
    outline = outlineLightHighContrast,
    outlineVariant = outlineVariantLightHighContrast,
    scrim = scrimLightHighContrast,
    inverseSurface = inverseSurfaceLightHighContrast,
    inverseOnSurface = inverseOnSurfaceLightHighContrast,
    inversePrimary = inversePrimaryLightHighContrast,
    surfaceDim = surfaceDimLightHighContrast,
    surfaceBright = surfaceBrightLightHighContrast,
    surfaceContainerLowest = surfaceContainerLowestLightHighContrast,
    surfaceContainerLow = surfaceContainerLowLightHighContrast,
    surfaceContainer = surfaceContainerLightHighContrast,
    surfaceContainerHigh = surfaceContainerHighLightHighContrast,
    surfaceContainerHighest = surfaceContainerHighestLightHighContrast,
)

private val mediumContrastDarkColorScheme = darkColorScheme(
    primary = primaryDarkMediumContrast,
    onPrimary = onPrimaryDarkMediumContrast,
    primaryContainer = primaryContainerDarkMediumContrast,
    onPrimaryContainer = onPrimaryContainerDarkMediumContrast,
    secondary = secondaryDarkMediumContrast,
    onSecondary = onSecondaryDarkMediumContrast,
    secondaryContainer = secondaryContainerDarkMediumContrast,
    onSecondaryContainer = onSecondaryContainerDarkMediumContrast,
    tertiary = tertiaryDarkMediumContrast,
    onTertiary = onTertiaryDarkMediumContrast,
    tertiaryContainer = tertiaryContainerDarkMediumContrast,
    onTertiaryContainer = onTertiaryContainerDarkMediumContrast,
    error = errorDarkMediumContrast,
    onError = onErrorDarkMediumContrast,
    errorContainer = errorContainerDarkMediumContrast,
    onErrorContainer = onErrorContainerDarkMediumContrast,
    background = backgroundDarkMediumContrast,
    onBackground = onBackgroundDarkMediumContrast,
    surface = surfaceDarkMediumContrast,
    onSurface = onSurfaceDarkMediumContrast,
    surfaceVariant = surfaceVariantDarkMediumContrast,
    onSurfaceVariant = onSurfaceVariantDarkMediumContrast,
    outline = outlineDarkMediumContrast,
    outlineVariant = outlineVariantDarkMediumContrast,
    scrim = scrimDarkMediumContrast,
    inverseSurface = inverseSurfaceDarkMediumContrast,
    inverseOnSurface = inverseOnSurfaceDarkMediumContrast,
    inversePrimary = inversePrimaryDarkMediumContrast,
    surfaceDim = surfaceDimDarkMediumContrast,
    surfaceBright = surfaceBrightDarkMediumContrast,
    surfaceContainerLowest = surfaceContainerLowestDarkMediumContrast,
    surfaceContainerLow = surfaceContainerLowDarkMediumContrast,
    surfaceContainer = surfaceContainerDarkMediumContrast,
    surfaceContainerHigh = surfaceContainerHighDarkMediumContrast,
    surfaceContainerHighest = surfaceContainerHighestDarkMediumContrast,
)

private val highContrastDarkColorScheme = darkColorScheme(
    primary = primaryDarkHighContrast,
    onPrimary = onPrimaryDarkHighContrast,
    primaryContainer = primaryContainerDarkHighContrast,
    onPrimaryContainer = onPrimaryContainerDarkHighContrast,
    secondary = secondaryDarkHighContrast,
    onSecondary = onSecondaryDarkHighContrast,
    secondaryContainer = secondaryContainerDarkHighContrast,
    onSecondaryContainer = onSecondaryContainerDarkHighContrast,
    tertiary = tertiaryDarkHighContrast,
    onTertiary = onTertiaryDarkHighContrast,
    tertiaryContainer = tertiaryContainerDarkHighContrast,
    onTertiaryContainer = onTertiaryContainerDarkHighContrast,
    error = errorDarkHighContrast,
    onError = onErrorDarkHighContrast,
    errorContainer = errorContainerDarkHighContrast,
    onErrorContainer = onErrorContainerDarkHighContrast,
    background = backgroundDarkHighContrast,
    onBackground = onBackgroundDarkHighContrast,
    surface = surfaceDarkHighContrast,
    onSurface = onSurfaceDarkHighContrast,
    surfaceVariant = surfaceVariantDarkHighContrast,
    onSurfaceVariant = onSurfaceVariantDarkHighContrast,
    outline = outlineDarkHighContrast,
    outlineVariant = outlineVariantDarkHighContrast,
    scrim = scrimDarkHighContrast,
    inverseSurface = inverseSurfaceDarkHighContrast,
    inverseOnSurface = inverseOnSurfaceDarkHighContrast,
    inversePrimary = inversePrimaryDarkHighContrast,
    surfaceDim = surfaceDimDarkHighContrast,
    surfaceBright = surfaceBrightDarkHighContrast,
    surfaceContainerLowest = surfaceContainerLowestDarkHighContrast,
    surfaceContainerLow = surfaceContainerLowDarkHighContrast,
    surfaceContainer = surfaceContainerDarkHighContrast,
    surfaceContainerHigh = surfaceContainerHighDarkHighContrast,
    surfaceContainerHighest = surfaceContainerHighestDarkHighContrast,
)

@Immutable
data class ColorFamily(
    val color: Color,
    val onColor: Color,
    val colorContainer: Color,
    val onColorContainer: Color
)

val unspecified_scheme = ColorFamily(
    Color.Unspecified, Color.Unspecified, Color.Unspecified, Color.Unspecified
)

@Composable
fun FurryRoyalsTheme(
    darkTheme: Boolean = false,
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable() () -> Unit
) {
  val colorScheme = when {
      dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
          val context = LocalContext.current
          if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
      }
      
      darkTheme -> darkScheme
      else -> highContrastLightColorScheme
  }

  MaterialTheme(
    colorScheme = colorScheme,
    typography = typography,
    content = content
  )
}

