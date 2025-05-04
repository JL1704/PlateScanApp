package com.deltasquad.platescanapp.presentation.components

import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ContentCardGroup(modifier: Modifier = Modifier) {
    val sampleData = listOf(
        Triple(Uri.parse("content://media/external/images/media/1000000072"), "8806 KZS", "01/02/2025"),
        Triple(Uri.parse("content://media/external/images/media/1000000073"), "1234 ABC", "05/03/2025"),
        Triple(Uri.parse("content://media/external/images/media/1000000074"), "5678 DEF", "12/04/2025"),
        Triple(Uri.parse("content://media/external/images/media/1000000075"), "9012 GHI", "20/05/2025"),
        Triple(Uri.parse("content://media/external/images/media/1000000076"), "3456 JKL", "15/06/2025")
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        sampleData.forEach { (imageUri, plateNumber, date) ->
            ContentCard(
                croppedImage = imageUri,
                plate = plateNumber,
                date = date,
                state = "Success",
                onClick = {}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ContentCardGroupPreview() {
    ContentCardGroup()
}

