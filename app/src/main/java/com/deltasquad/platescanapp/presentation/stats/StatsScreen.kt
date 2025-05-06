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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.deltasquad.platescanapp.presentation.components.*
import coil.compose.AsyncImage
import com.deltasquad.platescanapp.R
import com.deltasquad.platescanapp.presentation.theme.PlateScanAppTheme

@Composable
fun StatsScreen(onBackClick: () -> Unit = {}) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Esta es la pantala stats")

        Spacer(modifier = Modifier.height(128.dp))

        AsyncImage(
            model = "https://cdn-icons-png.flaticon.com/512/8462/8462143.png",//"https://via.placeholder.com/150",
            contentDescription = "Work in progress",
            modifier = Modifier
                .size(300.dp)
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