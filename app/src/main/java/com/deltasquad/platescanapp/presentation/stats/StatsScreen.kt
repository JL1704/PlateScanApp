package com.deltasquad.platescanapp.presentation.stats

import android.view.ViewGroup
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.deltasquad.platescanapp.R
import com.deltasquad.platescanapp.presentation.components.SectionLabel
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import android.graphics.Color
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

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
                text = "Stats",
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(end = 44.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
        }

        Text("Scans Per Day", style = MaterialTheme.typography.titleMedium)
        BarChartView("Scans", scans, maxBars = 7)

        Text("Monthly Reports", style = MaterialTheme.typography.titleMedium)
        BarChartView("Reports", reports, maxBars = 6)
    }
}


@Composable
fun BarChartView(
    title: String,
    dataMap: Map<String, Int>,
    maxBars: Int
) {
    if (dataMap.isEmpty()) {
        Text("No data available")
        return
    }

    val sortedData = dataMap.toSortedMap().toList().takeLast(maxBars)
    val entries = sortedData.mapIndexed { index, entry ->
        BarEntry(index.toFloat(), entry.second.toFloat())
    }
    val labels = sortedData.map { it.first }

    AndroidView(
        factory = { context ->
            BarChart(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    600
                )

                // Fondo oscuro
                setBackgroundColor(Color.TRANSPARENT)

                val dataSet = BarDataSet(entries, title).apply {
                    setColor(Color.rgb(30, 144, 255)) // Azul claro para barras
                    valueTextColor = Color.rgb(17, 185, 123)
                    valueTextSize = 16f
                }
                this.data = BarData(dataSet)

                // Ejes
                xAxis.apply {
                    valueFormatter = IndexAxisValueFormatter(labels)
                    granularity = 1f
                    setDrawGridLines(false)
                    setLabelCount(labels.size)
                    textColor = Color.rgb(17, 185, 123)
                    textSize = 14f
                    position = com.github.mikephil.charting.components.XAxis.XAxisPosition.BOTTOM
                }

                axisLeft.apply {
                    textColor = Color.rgb(17, 185, 123)
                    gridColor = Color.GRAY
                    axisLineColor = Color.GRAY
                }

                axisRight.isEnabled = false

                // Otros ajustes
                description.isEnabled = false
                legend.isEnabled = false
                animateY(1000)
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
    )
}