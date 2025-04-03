package com.deltasquad.platescanapp.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import com.deltasquad.platescanapp.R

@Composable
fun ButtonGroup() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        NavigationButton(
            text = "Ver Registros",
            iconRes = R.drawable.ic_history // Reemplaza con tu ícono
        )
        NavigationButton(
            text = "Reportes",
            iconRes = R.drawable.ic_reports // Reemplaza con tu ícono
        )
        NavigationButton(
            text = "Otros",
            iconRes = R.drawable.ic_other // Reemplaza con tu ícono
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ButtonGroupPreview() {
    ButtonGroup()
}
