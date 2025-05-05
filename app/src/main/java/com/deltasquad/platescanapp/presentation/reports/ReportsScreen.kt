package com.deltasquad.platescanapp.presentation.reports

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.deltasquad.platescanapp.presentation.theme.PlateScanAppTheme

@Composable
fun ReportsScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Esta es la pantalla reports")

        Spacer(modifier = Modifier.height(16.dp)) // Espaciado opcional

        AsyncImage(
            model = "https://cdn-icons-png.flaticon.com/512/8462/8462143.png",
            contentDescription = "Work in progress",
            modifier = Modifier.size(300.dp)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun ReportsPreview(){
    PlateScanAppTheme{
        ReportsScreen()
    }
}