package com.deltasquad.platescanapp.presentation.camera

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.deltasquad.platescanapp.presentation.theme.PlateScanAppTheme


@Composable
fun CameraScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = {
            // Aquí puedes abrir la cámara
        }) {
            Text("Abrir cámara")
        }
    }
}

@Preview(
    name = "CameraScreenPreview",
    showBackground = true
)
@Composable
fun CameraScreenPreview() {
    PlateScanAppTheme {
        CameraScreen()
    }
}