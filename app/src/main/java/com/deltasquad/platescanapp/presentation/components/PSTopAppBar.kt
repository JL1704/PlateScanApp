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
import androidx.compose.ui.Alignment
import com.deltasquad.platescanapp.R
import com.deltasquad.platescanapp.presentation.theme.PlateScanAppTheme
import com.deltasquad.platescanapp.presentation.theme.primaryGreen
import com.deltasquad.platescanapp.presentation.theme.primaryWhite

/**
 * Composable que representa la barra superior de la aplicación.
 *
 * @param onMenuClick Callback que se ejecuta al hacer clic en el botón del menú.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PSTopAppBar(onMenuClick: () -> Unit) {
    TopAppBar(
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Botón de menú en la parte izquierda de la barra superior
                IconButton(onClick = onMenuClick) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Menu Icon",
                        tint = primaryWhite
                    )
                }

                // Espaciador para equilibrar el diseño y evitar que el logo se alinee a la izquierda
                Spacer(modifier = Modifier.width(PlateScanAppTheme.dimens.spacerMedium)) // Ajusta según sea necesario

                // Contenedor del logo centrado horizontalmente
                Box(
                    modifier = Modifier.weight(1f), // Permite centrar el logo en la barra
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = "App Logo",
                        modifier = Modifier.height(PlateScanAppTheme.dimens.imageHeightNormal)
                    )
                }

                // Espaciador para balancear la distribución de los elementos
                Spacer(modifier = Modifier.width(PlateScanAppTheme.dimens.spacerLarge)) // Ajusta si es necesario
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = primaryGreen) // Define el color de fondo de la barra
    )
}

/**
 * Vista previa del componente PSTopAppBar.
 */
@Preview(showBackground = true)
@Composable
fun PSTopAppBarPreview() {
    PlateScanAppTheme {
        PSTopAppBar(onMenuClick = {})
    }
}
