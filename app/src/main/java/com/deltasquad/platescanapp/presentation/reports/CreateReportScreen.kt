package com.deltasquad.platescanapp.presentation.reports

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.deltasquad.platescanapp.R
import com.deltasquad.platescanapp.presentation.components.SectionLabel
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.platform.LocalContext
import coil.compose.rememberAsyncImagePainter
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

@Composable
fun CreateReportScreen(
    navController: NavHostController,
    viewModel: CreateReportViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val userPlates by viewModel.userPlates.collectAsState()
    var expanded by remember { mutableStateOf(false) }

    val selectedPlate by viewModel.selectedPlate
    val reportType by viewModel.reportType
    val description by viewModel.description
    val isSaving by viewModel.isSaving

    // Lista de tipos de reporte frecuentes
    val reportTypes = listOf(
        "Accident",
        "Badly parked",
        "Garage obstruction",
        "Fake License Plate",
        "Dangerous Driving",
        "Abandoned vehicle",
        "Improper use of rails",
        "Environmental pollution",
        "Stolen vehicle",
        "Lack of verification"
    )

    var reportTypeExpanded by remember { mutableStateOf(false) }

    val context = LocalContext.current
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val scrollState = rememberScrollState()

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            imageUri = it
            // Guardar imagen localmente
            val inputStream: InputStream? = context.contentResolver.openInputStream(it)
            val file = File(context.filesDir, "evidence_${System.currentTimeMillis()}.jpg")
            inputStream?.use { input ->
                FileOutputStream(file).use { output ->
                    input.copyTo(output)
                }
            }
            // Guardar ruta en el ViewModel
            viewModel.setEvidenceImagePath(file.absolutePath)
        }
    }


    LaunchedEffect(Unit) {
        viewModel.fetchUserPlates()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Encabezado...
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
                text = "New Report",
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(end = 44.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Selector de placa
        Column {
            Text(text = "Plate", style = MaterialTheme.typography.labelLarge)
            Box(modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = true }) {
                OutlinedTextField(
                    value = selectedPlate,
                    onValueChange = {},
                    readOnly = true,
                    enabled = false, // evita foco y teclado
                    label = { Text("Select a plate") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                userPlates.forEach { scan ->
                    DropdownMenuItem(
                        text = { Text(scan.plate) },
                        onClick = {
                            viewModel.onPlateSelected(scan.plate, scan.id)
                            expanded = false
                        }
                    )
                }
            }
        }

        Column {
            Text(text = "Report Type", style = MaterialTheme.typography.labelLarge)
            Box(modifier = Modifier
                .fillMaxWidth()
                .clickable { reportTypeExpanded = true }) {
                OutlinedTextField(
                    value = reportType,
                    onValueChange = {},
                    readOnly = true,
                    enabled = false, // evita foco y teclado
                    label = { Text("Select a type") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
            DropdownMenu(
                expanded = reportTypeExpanded,
                onDismissRequest = { reportTypeExpanded = false }
            ) {
                reportTypes.forEach { type ->
                    DropdownMenuItem(
                        text = { Text(type) },
                        onClick = {
                            viewModel.onReportTypeChanged(type)
                            reportTypeExpanded = false
                        }
                    )
                }
            }
        }

        OutlinedTextField(
            value = description,
            onValueChange = { viewModel.onDescriptionChanged(it) },
            label = { Text("Description") },
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
        )

        // Botón para seleccionar imagen
        Button(
            onClick = { launcher.launch("image/*") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Attach Image Evidence")
        }

        // Muestra previa de la imagen seleccionada
        imageUri?.let {
            Image(
                painter = rememberAsyncImagePainter(it),
                contentDescription = "Selected Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            )
        }
        Button(
            onClick = {
                viewModel.createReport(
                    onSuccess = { navController.popBackStack() },
                    onError = { /* Manejar error */ }
                )
            },
            enabled = selectedPlate.isNotBlank() && reportType.isNotBlank() && description.isNotBlank(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (isSaving) "Saving..." else "Save Report")
        }
    }
}
