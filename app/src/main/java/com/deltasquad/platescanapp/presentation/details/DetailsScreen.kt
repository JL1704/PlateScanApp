package com.deltasquad.platescanapp.presentation.details

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import com.deltasquad.platescanapp.presentation.components.CommentsSection
import com.deltasquad.platescanapp.presentation.components.ImageSection
import com.deltasquad.platescanapp.presentation.components.ScanDataSection
import com.deltasquad.platescanapp.presentation.components.SectionLabel
import com.deltasquad.platescanapp.presentation.components.VehicleDataSection
import com.deltasquad.platescanapp.presentation.navigation.Screen
import com.deltasquad.platescanapp.presentation.theme.primaryGreen

@Composable
fun DetailsScreen(
    scanId: String,
    navController: NavController, // Asegúrate de tener el navController para la navegación
    viewModel: DetailViewModel = viewModel()
) {
    LaunchedEffect(scanId) {
        viewModel.fetchScanById(scanId)
    }

    val scanRecord by viewModel.scanRecord.collectAsState()

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
                    text = scanRecord?.plate.toString(),
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(end = 44.dp)
                )

                Spacer(modifier = Modifier.weight(1f))
            }
        }

        // Contenido dentro del LazyColumn
        scanRecord?.let { record ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                item {
                    ScanDataSection(record)
                    Spacer(modifier = Modifier.height(16.dp))
                    VehicleDataSection(record)
                    Spacer(modifier = Modifier.height(16.dp))
                    CommentsSection(record)
                    Spacer(modifier = Modifier.height(16.dp))
                    ImageSection(record)
                    Spacer(modifier = Modifier.height(16.dp))
                }

                // Botones de Editar y Eliminar en la parte inferior
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Button(
                            onClick = {
                                navController.navigate(Screen.EditData.createRoute(scanId))
                            },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(containerColor = primaryGreen)
                        ) {
                            Text("Edit", color = Color.White, fontWeight = FontWeight.Bold)
                        }
                        Button(
                            onClick = {
                                viewModel.deleteScanById(
                                    scanId = scanId,
                                    onSuccess = {
                                        navController.popBackStack() // Navega hacia atrás al eliminar exitosamente
                                    },
                                    onFailure = {
                                        // Puedes mostrar un Snackbar o Toast aquí si quieres informar del error
                                    }
                                )
                            },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                        ) {
                            Text("Delete", color = Color.White, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        } ?: Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator()
        }
    }
}
