package com.deltasquad.platescanapp.presentation.theme

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Dimens(
    val bottomNavViewHeight: Dp = 90.dp,
    val imageHeightNormal: Dp = 40.dp,
    val roundedShapeMedium: Dp = 16.dp,
    val roundedShapeLarge: Dp = 20.dp,
    val iconSizeLarge: Dp = 40.dp,
    val spacerVerySmall: Dp = 4.dp,

    val borderNormal: Dp = 4.dp,
    val buttonHeightNormal: Dp = 56.dp,
    val iconSizeSmall: Dp = 24.dp,
    val iconSizeNormal: Dp = 36.dp,
    val paddingSmall: Dp = 4.dp,
    val paddingNormal: Dp = 8.dp,
    val paddingMedium: Dp = 16.dp,
    val roundedShapeNormal: Dp = 12.dp,
    val spacerSmall: Dp = 8.dp,
    val spacerNormal: Dp = 16.dp,
    val spacerMedium: Dp = 40.dp,
    val spacerLarge: Dp = 100.dp,
)

val DefaultDimens = Dimens()

//Dimensions for tablet
val TabletDimens = Dimens(
    buttonHeightNormal = 64.dp,
    iconSizeSmall = 36.dp,
    iconSizeNormal = 48.dp,
    paddingSmall = 8.dp,
    paddingNormal = 16.dp,
    paddingMedium = 24.dp,
    roundedShapeNormal = 16.dp,
    spacerSmall = 8.dp,
    spacerNormal = 16.dp,
    spacerMedium = 24.dp,
    spacerLarge = 56.dp,
)