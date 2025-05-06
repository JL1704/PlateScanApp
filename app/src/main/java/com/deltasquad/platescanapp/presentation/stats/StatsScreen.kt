package com.deltasquad.platescanapp.presentation.stats

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.deltasquad.platescanapp.presentation.components.*
import com.deltasquad.platescanapp.R
import com.deltasquad.platescanapp.presentation.theme.PlateScanAppTheme

@Composable
fun StatsScreen(onBackClick: () -> Unit = {}) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onBackClick() }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_back_24),
                contentDescription = "Volver"
            )
            Spacer(modifier = Modifier.width(8.dp))
            Box(modifier = Modifier.fillMaxWidth()) {
                SectionLabel(
                    text = "Stats",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
        StatCard(
            title = "Escaneos por día",
            content = "Hoy: 23 escaneos"
        )

        StatCard(
            title = "Reportes Mensuales",
            content = "Este mes: 12 reportes"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RecordsPreview(){
    PlateScanAppTheme{
        StatsScreen()
    }
}