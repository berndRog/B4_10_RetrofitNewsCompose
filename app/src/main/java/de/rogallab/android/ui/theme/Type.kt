package de.rogallab.android.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val AppTypography = Typography(
   h5 = TextStyle(
      fontFamily = FontFamily.Default,
      fontWeight = FontWeight.Normal,
      fontSize = 28.sp
   ),
   h6 = TextStyle(
      fontFamily = FontFamily.Default,
      fontWeight = FontWeight.Normal,
      fontSize = 24.sp
   ),
   subtitle1 = TextStyle(
      fontFamily = FontFamily.Default,
      fontSize = 18.sp
   ),
   subtitle2 = TextStyle(
      fontFamily = FontFamily.Default,
      fontSize = 16.sp
   ),
   body1 = TextStyle(
      fontFamily = FontFamily.Default,
      fontWeight = FontWeight.W500,
      fontSize = 18.sp
   ),
   body2 = TextStyle(
      fontFamily = FontFamily.Default,
      fontSize = 16.sp
   ),
   button = TextStyle(
      fontFamily = FontFamily.Default,
      fontSize = 18.sp
   ),
   caption = TextStyle(
      fontFamily = FontFamily.Default,
      fontSize = 14.sp
   ),
   overline = TextStyle(
      fontFamily = FontFamily.Default,
      fontSize = 14.sp
   )
)
