package com.deltasquad.platescanapp.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.deltasquad.platescanapp.presentation.theme.PlateScanAppTheme

@Composable
fun ContentCard(
    imageUrl: String,
    plateNumber: String,
    date: String,
    description: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(PlateScanAppTheme.dimens.paddingNormal)
            .clickable { onClick() },
        shape = RoundedCornerShape(PlateScanAppTheme.dimens.roundedShapeMedium),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(PlateScanAppTheme.dimens.paddingNormal),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = imageUrl,
                contentDescription = "Placa del vehículo",
                modifier = Modifier
                    .size(80.dp)
                    .padding(end = PlateScanAppTheme.dimens.paddingNormal),
                error = painterResource(com.deltasquad.platescanapp.R.drawable.placeholder), // Imagen de respaldo
                placeholder = painterResource(com.deltasquad.platescanapp.R.drawable.placeholder) // Imagen mientras carga
            )
            Column {
                Text(
                    text = plateNumber,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Text(text = date, fontSize = 14.sp, color = Color.Gray)
                Text(text = "Descripción: $description", fontSize = 14.sp)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ContentCardPreview() {
    ContentCard(
        imageUrl = "https://taxielegant.com/wp-content/uploads/2023/08/tipos-matriculas-espana.jpg",
        plateNumber = "8806 KZS",
        date = "01/02/2025",
        description = "Esta es una placa",
        onClick = {}
    )
}
