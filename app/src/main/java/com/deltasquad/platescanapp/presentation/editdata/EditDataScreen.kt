package com.deltasquad.platescanapp.presentation.editdata

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.deltasquad.platescanapp.R
import com.deltasquad.platescanapp.presentation.components.SectionLabel
import com.deltasquad.platescanapp.presentation.details.DetailViewModel
import com.deltasquad.platescanapp.presentation.theme.primaryGreen

@Composable
fun EditDataScreen(
    scanId: String,
    navController: NavController,
    viewModel: DetailViewModel = viewModel()
) {
    val scanRecord by viewModel.scanRecord.collectAsState()

    // Llenamos los campos con los datos actuales
    var vehicleType by remember { mutableStateOf("") }
    var vehicleColor by remember { mutableStateOf("") }
    var makeModel by remember { mutableStateOf("") }
    var purposeScanning by remember { mutableStateOf("") }
    var comments by remember { mutableStateOf("") }

    LaunchedEffect(scanId) {
        viewModel.fetchScanById(scanId)
    }

    LaunchedEffect(scanRecord) {
        scanRecord?.let {
            vehicleType = it.vehicleType
            vehicleColor = it.vehicleColor
            makeModel = it.makeModel
            purposeScanning = it.purposeScanning
            comments = it.comments
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Parte superior con el Box (icono de retroceso y título)
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
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .size(28.dp)
                        .clickable {
                            navController.popBackStack()
                        }
                )

                Spacer(modifier = Modifier.weight(1f))

                SectionLabel(
                    text = "Edit Scan Data",
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(end = 44.dp)
                )

                Spacer(modifier = Modifier.weight(1f))
            }
        }

        scanRecord?.let {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = vehicleType,
                    onValueChange = { vehicleType = it },
                    label = { Text("Vehicle Type") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = vehicleColor,
                    onValueChange = { vehicleColor = it },
                    label = { Text("Vehicle Color") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = makeModel,
                    onValueChange = { makeModel = it },
                    label = { Text("Make & Model") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = purposeScanning,
                    onValueChange = { purposeScanning = it },
                    label = { Text("Purpose of Scanning") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = comments,
                    onValueChange = { comments = it },
                    label = { Text("Comments") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        val updatedData = mapOf(
                            "vehicleType" to vehicleType,
                            "vehicleColor" to vehicleColor,
                            "makeModel" to makeModel,
                            "purposeScanning" to purposeScanning,
                            "comments" to comments
                        )
                        viewModel.updateScanData(
                            scanId = scanId,
                            updatedData = updatedData,
                            onSuccess = {
                                navController.popBackStack() // Regresa a la pantalla anterior
                            },
                            onFailure = {
                                // Mostrar error si se desea
                            }
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(primaryGreen)
                ) {
                    Text("Save Changes", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        } ?: Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}
