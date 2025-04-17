package com.deltasquad.platescanapp.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ContentCardGroup(modifier: Modifier = Modifier) {
    val sampleData = listOf(
        Triple("https://taxielegant.com/wp-content/uploads/2023/08/tipos-matriculas-espana.jpg", "8806 KZS", "01/02/2025"),
        Triple("https://taxielegant.com/wp-content/uploads/2023/08/tipos-matriculas-espana.jpg", "1234 ABC", "05/03/2025"),
        Triple("https://taxielegant.com/wp-content/uploads/2023/08/tipos-matriculas-espana.jpg", "5678 DEF", "12/04/2025"),
        Triple("https://taxielegant.com/wp-content/uploads/2023/08/tipos-matriculas-espana.jpg", "9012 GHI", "20/05/2025"),
        Triple("https://taxielegant.com/wp-content/uploads/2023/08/tipos-matriculas-espana.jpg", "3456 JKL", "15/06/2025")
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        sampleData.forEach { (imageUrl, plateNumber, date) ->
            ContentCard(
                imageUrl = imageUrl,
                plateNumber = plateNumber,
                date = date,
                description = "Esta es una placa",
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
