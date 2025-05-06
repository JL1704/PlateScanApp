package com.deltasquad.platescanapp.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Alignment
import androidx.navigation.NavHostController
import com.deltasquad.platescanapp.R
import com.deltasquad.platescanapp.presentation.navigation.Screen

@Composable
fun ButtonGroup(navController: NavHostController) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        NavigationButton(
            text = "Records",
            iconRes = R.drawable.ic_history,
            onClick = { navController.navigate(Screen.Records.route) }
        )
        NavigationButton(
            text = "Reports",
            iconRes = R.drawable.ic_reports,
            onClick = { navController.navigate(Screen.Reports.route) }
        )
        NavigationButton(
            text = "Stats",
            iconRes = R.drawable.ic_stats,
            onClick = { navController.navigate(Screen.Stats.route) }
        )
    }
}


@Preview(showBackground = true)
@Composable
fun ButtonGroupPreview() {
    //ButtonGroup()
}
