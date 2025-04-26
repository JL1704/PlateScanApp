package com.deltasquad.platescanapp.presentation.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat


private val DarkColorScheme = darkColorScheme(
    primary = primaryGreen,
    secondary = primaryWhite,
    tertiary = primaryBrown,
    surface = primaryBlack,  // ← superficie oscura
    background = primaryBlack // ← fondo oscuro real
)


private val LightColorScheme = lightColorScheme(
    primary = primaryGreen,
    secondary = primaryBlack,
    tertiary = primaryBrown,
    surface = primaryWhite,
    background = primaryWhite
)


private val LocalDimens = staticCompositionLocalOf { DefaultDimens }

@Composable
fun ProvideDimens(
    dimens: Dimens,
    content: @Composable () -> Unit
){
    val dimensionSet = remember { dimens }
    CompositionLocalProvider(LocalDimens provides dimensionSet, content = content)
}

@Composable
fun PlateScanAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    windowSize: WindowWidthSizeClass = WindowWidthSizeClass.Compact,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            // Cambia el color de la barra de estado según el tema
            window.statusBarColor = if (darkTheme) {
                Color.Black.toArgb()
            } else {
                Color.Red.toArgb()  // Aquí puedes cambiar a cualquier color
            }
            // Controla si los íconos deben ser oscuros (true) o claros (false)
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    val dimensions = if (windowSize > WindowWidthSizeClass.Compact)
        TabletDimens
    else
        DefaultDimens

    ProvideDimens(dimens = dimensions) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}


object PlateScanAppTheme{
    val dimens: Dimens
        @Composable
        @ReadOnlyComposable
        get() = LocalDimens.current
}