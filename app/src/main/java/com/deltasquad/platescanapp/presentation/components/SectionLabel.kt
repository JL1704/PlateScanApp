package com.deltasquad.platescanapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.deltasquad.platescanapp.presentation.theme.PlateScanAppTheme
import com.deltasquad.platescanapp.presentation.theme.primaryGreen
import com.deltasquad.platescanapp.presentation.theme.primaryWhite

@Composable
fun SectionLabel(text: String) {
    Box(
        modifier = Modifier
            .padding(horizontal = PlateScanAppTheme.dimens.paddingMedium, vertical = PlateScanAppTheme.dimens.paddingNormal)
            .background(primaryGreen, shape = RoundedCornerShape(PlateScanAppTheme.dimens.roundedShapeMedium)) // Verde con bordes redondeados
            .padding(horizontal = PlateScanAppTheme.dimens.paddingMedium, vertical = PlateScanAppTheme.dimens.paddingNormal)
    ) {
        Text(
            text = text,
            color = primaryWhite,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SectionLabelPreview() {
    SectionLabel(text = "Registros Recientes")
}
