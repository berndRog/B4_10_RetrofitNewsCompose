package de.rogallab.android.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class Elevation(
   val default: Dp = 0.dp,
   val card:    Dp = 4.dp,
   val small:   Dp = 8.dp
)

internal val localElevations = staticCompositionLocalOf { Elevation() }

// extensions MaterialTheme.paddings
val MaterialTheme.elevations: Elevation
   @Composable
   @ReadOnlyComposable
   get() = localElevations.current