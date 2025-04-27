package com.deltasquad.platescanapp.presentation.reports

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.deltasquad.platescanapp.presentation.theme.PlateScanAppTheme

@Composable
fun ReportsScreen(){
    Column(
        modifier = Modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Esta es la pantala reports")
    }
}

@Preview(showBackground = true)
@Composable
fun ReportsPreview(){
    PlateScanAppTheme{
        ReportsScreen()
    }
}