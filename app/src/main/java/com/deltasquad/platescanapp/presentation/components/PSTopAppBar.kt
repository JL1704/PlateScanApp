package com.deltasquad.platescanapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.Alignment
import com.deltasquad.platescanapp.R
import com.deltasquad.platescanapp.presentation.theme.PlateScanAppTheme
import com.deltasquad.platescanapp.presentation.theme.primaryGreen
import com.deltasquad.platescanapp.presentation.theme.primaryWhite

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PSTopAppBar(onMenuClick: () -> Unit) {
    TopAppBar(
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Icono del menú
                IconButton(onClick = onMenuClick) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Menu Icon",
                        tint = primaryWhite
                    )
                }

                // Agregar un Spacer que compense el ancho del IconButton
                Spacer(modifier = Modifier.width(40.dp)) // Ajusta el valor según sea necesario

                // Logo centrado respecto a la pantalla
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = "App Logo",
                        modifier = Modifier.height(40.dp)
                    )
                }

                // Spacer vacío para equilibrar el diseño
                Spacer(modifier = Modifier.width(100.dp)) // Ajusta si es necesario
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = primaryGreen)
    )
}


@Preview(showBackground = true)
@Composable
fun PSTopAppBarPreview() {
    PlateScanAppTheme {
        PSTopAppBar(onMenuClick = {})
    }
}
