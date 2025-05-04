package com.deltasquad.platescanapp.presentation.components

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.deltasquad.platescanapp.R
import com.deltasquad.platescanapp.data.model.ScanRecord

@Composable
fun ImageSection(record: ScanRecord) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
        Text(text = "Imagen Original", color = Color.DarkGray)
        AsyncImage(
            model = Uri.parse(record.image),
            contentDescription = "Imagen original del escaneo",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(vertical = 8.dp),
            placeholder = painterResource(id = R.drawable.placeholder),
            error = painterResource(id = R.drawable.placeholder)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Imagen Recortada", color = Color.DarkGray)
        AsyncImage(
            model = Uri.parse(record.croppedImage),
            contentDescription = "Imagen recortada de la placa",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(vertical = 8.dp),
            placeholder = painterResource(id = R.drawable.placeholder),
            error = painterResource(id = R.drawable.placeholder)
        )
    }
}
