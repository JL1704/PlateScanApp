package com.deltasquad.platescanapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.deltasquad.platescanapp.R
import com.deltasquad.platescanapp.presentation.theme.PlateScanAppTheme
import com.deltasquad.platescanapp.presentation.theme.primaryBrown
import com.deltasquad.platescanapp.presentation.theme.primaryWhite

@Composable
fun NavigationButton(
    text: String,
    iconRes: Int,
    backgroundColor: Color = primaryBrown,
    contentColor: Color = primaryWhite,
    onClick: () -> Unit // <-- AÑADIR ESTO
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(PlateScanAppTheme.dimens.paddingNormal)
            .clickable { onClick() } // <-- Maneja el clic aquí
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .background(backgroundColor, shape = RoundedCornerShape(PlateScanAppTheme.dimens.roundedShapeLarge)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = text,
                tint = contentColor,
                modifier = Modifier.size(PlateScanAppTheme.dimens.iconSizeLarge)
            )
        }
        Spacer(modifier = Modifier.height(PlateScanAppTheme.dimens.spacerVerySmall))
        Text(
            text = text,
            fontSize = 14.sp,
            color = primaryBrown
        )
    }
}


@Preview(showBackground = true)
@Composable
fun NavigationButtonPreview() {

}
