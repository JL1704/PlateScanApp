package com.deltasquad.platescanapp.presentation.records

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.deltasquad.platescanapp.R
import com.deltasquad.platescanapp.presentation.components.ContentCard
import com.deltasquad.platescanapp.presentation.components.SearchBar
import com.deltasquad.platescanapp.presentation.components.SectionLabel
import com.deltasquad.platescanapp.presentation.theme.PlateScanAppTheme
import com.deltasquad.platescanapp.presentation.theme.primaryBrown

@Composable
fun RecordsScreen(navController: NavHostController, viewModel: RecordsViewModel = androidx.lifecycle.viewmodel.compose.viewModel()){

    val scans by viewModel.latestScans.collectAsState()
    var query by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.fetchLatestScans()
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, bottom = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_back_24),
                contentDescription = "Back",
                tint = primaryBrown,
                modifier = Modifier
                    .padding(start = 16.dp)
                    .size(28.dp)
                    .clickable {
                        navController.popBackStack()
                    }
            )

            Spacer(modifier = Modifier.weight(1f))

            SectionLabel(
                text = "All Records",
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(end = 44.dp)
            )

            Spacer(modifier = Modifier.weight(1f))
        }
    }


    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .padding(top = 72.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(bottom = 16.dp, top = 16.dp)
    ) {
        item {
            SearchBar(
                query = query,
                onQueryChanged = { query = it }
            )
        }

        items(scans) { scan ->
            ContentCard(
                croppedImage = Uri.parse(scan.croppedImage),
                plate = scan.plate,
                date = scan.date,
                state = scan.state,
                onClick = { /* Navegar a detalle si deseas */ }
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun RecordsPreview(){
    PlateScanAppTheme{
        //RecordsScreen()
    }
}