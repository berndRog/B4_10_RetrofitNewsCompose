package de.rogallab.android.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color

private val LightColorPalette = lightColors(
   primary = Color(0xFF43a047),  // green600
   primaryVariant = Color(0xFF2e7d32),  // green800
   secondary = Color(0xFFffc107),  // amber500
   secondaryVariant = Color(0xFFffc107),  // amber500
   background = Color(0xFFFFFFFF),
   surface = Color(0xFFFFFFFF),
   error = Color(0xFFB00020),
   onPrimary = Color(0xFFFFFFFF),
   onSecondary = Color(0xFF000000),
   onBackground = Color(0xFF000000),
   onSurface = Color(0xFF000000),
   onError = Color(0xFFFFFFFF)
)
private val DarkColorPalette = darkColors(
   primary = Color(0xFFa5d6a7),  // green200,
   primaryVariant = Color(0xFF2e7d32),  // green800
   secondary = Color(0xFFffe082),  // amber200
   secondaryVariant = Color(0xFFffb300),  // amber500
   background = Color(0xFF121212),
   surface = Color(0xFF121212),
   error = Color(0xFFef9a9a),
   // Text
   onPrimary = Color(0xFF000000),
   onSecondary = Color(0xFF000000),
   onBackground = Color(0xFFFFFFFF),
   onSurface = Color(0xFFFFFFFF),
   onError = Color(0xFF000000)
)

@Composable
fun AppTheme(
   darkTheme: Boolean = isSystemInDarkTheme(),
   content: @Composable () -> Unit
) {
   val colors = if (darkTheme) {
      DarkColorPalette
   } else {
      LightColorPalette
   }

   CompositionLocalProvider(
      localPaddings provides Padding(),
      localElevations provides Elevation()
   ) {
      MaterialTheme(
         colors = colors,
//       typography = AppTypography,
         shapes = Shapes,
         content = content
      )
   }

}

/*
object AppTheme {
   // Proxies to [MaterialTheme]
   val colors: Colors
      @Composable
      get() = MaterialTheme.colors
   val typography: Typography
      @Composable
      get() = MaterialTheme.typography
   val shapes: Shapes
      @Composable
      get() = MaterialTheme.shapes
   // Retrieves the current values
   val paddings: Padding
      @Composable
      get() = localPaddings.current
   val elevations: Elevation
      @Composable
      get() = localElevations.current
}
*/