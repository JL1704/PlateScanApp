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
import androidx.compose.ui.graphics.Color
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
            Box(
                modifier = Modifier.fillMaxWidth(), // Ocupa todo el espacio horizontal
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "App Logo",
                    modifier = Modifier
                        .height(40.dp)
                        .align(Alignment.Center) // Centra el logo en el espacio disponible
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = primaryGreen),
        navigationIcon = {
            IconButton(onClick = onMenuClick) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Menu Icon",
                    tint = primaryWhite
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun PSTopAppBarPreview() {
    PlateScanAppTheme {
        PSTopAppBar(onMenuClick = {})
    }
}
