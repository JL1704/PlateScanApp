package com.deltasquad.platescanapp.presentation.stats

import androidx.compose.foundation.background
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.deltasquad.platescanapp.presentation.components.*
import com.deltasquad.platescanapp.R
import com.deltasquad.platescanapp.presentation.theme.PlateScanAppTheme
import androidx.lifecycle.viewmodel.compose.viewModel
//import io.github.ehsannarmani.composecharts.bar.model.BarData
//import io.github.ehsannarmani.composecharts.bar.BarChart

@Composable
fun StatsScreen(
    navController: NavHostController,
    viewModel: StatsViewModel = viewModel(),
    userId: String
) {
    LaunchedEffect(Unit) {
        viewModel.loadStats(userId)
    }

    val scans = viewModel.scansPerDay.value
    val reports = viewModel.monthlyReports.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_back_24),
                contentDescription = "Back",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .size(28.dp)
                    .clickable {
                        navController.popBackStack()
                    }
            )
            Spacer(modifier = Modifier.weight(1f))
            SectionLabel(
                text = "Reports",
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(end = 44.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text("Escaneos por Día", style = MaterialTheme.typography.titleMedium)
        ChartSection(dataMap = scans, maxBars = 7) // últimos 7 días

        Text("Reportes Mensuales", style = MaterialTheme.typography.titleMedium)
        ChartSection(dataMap = reports, maxBars = 6) // últimos 6 meses
    }
}

@Composable
fun ChartSection(dataMap: Map<String, Int>, maxBars: Int) {
    val sortedData = dataMap.toSortedMap().toList().takeLast(maxBars)
    val barData = sortedData.mapIndexed { index, (label, value) ->
        /*BarData(
            value = value.toFloat(),
            label = label.takeLast(2) // día o mes
        )

         */
    }

    if (barData.isNotEmpty()) {
        /*BarChart(
            data = barData,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )

         */
    } else {
        Text("No hay datos disponibles")
    }
}


