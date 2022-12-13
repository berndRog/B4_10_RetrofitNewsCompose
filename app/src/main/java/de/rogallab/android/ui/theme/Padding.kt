package de.rogallab.android.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


data class Padding(
   val default: Dp = 0.dp,
   val tiny:    Dp = 4.dp,
   val small:   Dp = 8.dp,
   val medium:  Dp = 16.dp,
   val large:   Dp = 32.dp,
   val huge:    Dp = 64.dp
)

// local Composition: all other Composables can see this
internal val localPaddings = compositionLocalOf { Padding () }

// extensions MaterialTheme.paddings
val MaterialTheme.paddings: Padding
   @Composable
   @ReadOnlyComposable
   get() = localPaddings.current