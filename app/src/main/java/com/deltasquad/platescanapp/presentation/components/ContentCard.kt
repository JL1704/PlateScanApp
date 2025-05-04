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
import android.net.Uri
import coil.compose.AsyncImage
import com.deltasquad.platescanapp.presentation.theme.PlateScanAppTheme

@Composable
fun ContentCard(
    croppedImage: Uri,
    plate: String,
    date: String,
    state: String,
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
                model = croppedImage,
                contentDescription = "Placa del vehículo",
                modifier = Modifier
                    .size(80.dp)
                    .padding(end = PlateScanAppTheme.dimens.paddingNormal),
                error = painterResource(com.deltasquad.platescanapp.R.drawable.placeholder),
                placeholder = painterResource(com.deltasquad.platescanapp.R.drawable.placeholder)
            )
            Column {
                Text(
                    text = plate,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Text(text = date, fontSize = 14.sp, color = Color.Gray)
                Text(text = "State: $state", fontSize = 14.sp)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ContentCardPreview() {
    ContentCard(
        croppedImage = Uri.parse("content://media/external/images/media/1000000072"),
        plate = "8806 KZS",
        date = "01/02/2025",
        state = "success",
        onClick = {}
    )
}
