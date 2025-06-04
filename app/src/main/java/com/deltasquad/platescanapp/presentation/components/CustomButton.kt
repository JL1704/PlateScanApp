package com.deltasquad.platescanapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.deltasquad.platescanapp.presentation.theme.primaryWhite
import com.deltasquad.platescanapp.presentation.theme.secondaryBlack

@Composable
fun CustomButton(
    modifier: Modifier = Modifier,         // Permite personalizar el botón desde el exterior
    painter: Painter,                      // Imagen (ícono) a mostrar en el botón
    title: String,                         // Texto a mostrar en el botón
    onClick: () -> Unit                    // Acción a ejecutar al hacer clic
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .padding(horizontal = 32.dp)
            .background(secondaryBlack, CircleShape)       // Fondo negro y forma circular
            .border(2.dp, primaryWhite, CircleShape)       // Borde blanco
            .clickable { onClick() },                      // Soporte para clics
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            // Ícono (por ejemplo, logo de Google)
            Image(
                painter = painter,
                contentDescription = title,
                modifier = Modifier
                    .padding(start = 16.dp)
                    .size(20.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))

            // Texto del botón
            Text(
                text = title,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
    }
}
