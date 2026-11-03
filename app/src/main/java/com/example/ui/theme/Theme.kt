package com.example.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val CeaserDarkColorScheme = darkColorScheme(
    primary = CeaserViolet,
    onPrimary = CeaserTextPrimary,
    primaryContainer = CeaserVioletDark,
    onPrimaryContainer = CeaserTextPrimary,
    secondary = CeaserCyan,
    onSecondary = CeaserBlack,
    secondaryContainer = CeaserBorderSubtle,
    onSecondaryContainer = CeaserCyan,
    tertiary = CeaserVioletLight,
    onTertiary = CeaserBlack,
    background = CeaserBlack,
    onBackground = CeaserTextPrimary,
    surface = CeaserDarkSurface,
    onSurface = CeaserTextPrimary,
    surfaceVariant = CeaserSurfaceCard,
    onSurfaceVariant = CeaserTextSecondary,
    outline = CeaserBorderSubtle,
    outlineVariant = CeaserBorderActive,
    error = CeaserError,
    onError = CeaserTextPrimary
)

@Composable
fun CeaserTheme(
    darkTheme: Boolean = true, // CEASER is designed natively as a premium dark experience
    content: @Composable () -> Unit
) {
    val colorScheme = CeaserDarkColorScheme
    val view = LocalView.current

    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as? Activity)?.window
            if (window != null) {
                window.statusBarColor = CeaserBlack.toArgb()
                window.navigationBarColor = CeaserBlack.toArgb()
                val insetsController = WindowCompat.getInsetsController(window, view)
                insetsController.isAppearanceLightStatusBars = false
                insetsController.isAppearanceLightNavigationBars = false
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

// For backwards-compatibility with template references
@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    CeaserTheme(content = content)
}
