package com.deltasquad.platescanapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun LabelValue(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.padding(top = 12.dp)) {
        // Caja con borde y contenido
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color.Gray, RoundedCornerShape(12.dp))
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .align(Alignment.CenterStart)
        ) {
            Text(
                text = value,
                fontSize = 16.sp,
                color = Color.Black,
                fontWeight = FontWeight.Normal
            )
        }

        // Etiqueta "flotante"
        Text(
            text = label,
            fontSize = 13.sp,
            color = Color.Gray,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .padding(start = 16.dp)
                .background(MaterialTheme.colorScheme.background)
                .offset(y = (-10).dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LabelValuePreview() {
    Column(Modifier.padding(16.dp)) {
        LabelValue(
            label = "Username",
            value = "example1234"
        )

        Spacer(modifier = Modifier.height(16.dp))

        LabelValue(
            label = "Email",
            value = "user@example.com"
        )
    }
}



