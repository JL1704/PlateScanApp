package com.deltasquad.platescanapp.presentation.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.deltasquad.platescanapp.presentation.components.CircleImageView
import com.deltasquad.platescanapp.presentation.components.UserInfo
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ProfileScreen(
    auth: FirebaseAuth,
    navController: NavHostController,
    rootNavController: NavHostController
) {
    var selectedItem by remember { mutableStateOf(2) }
    val signedOut = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        CircleImageView(
            imageUrl = "https://img.freepik.com/vector-premium/perfil-avatar-hombre-icono-redondo_24640-14044.jpg",
            modifier = Modifier.size(120.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        UserInfo(
            username = "example1234",
            email = "user@example.com",
            phone = "+1 123 456 7890"
        )

        Spacer(modifier = Modifier.weight(1f)) // Empuja los botones hacia abajo

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 40.dp), // Espacio desde el fondo
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    // Aquí luego agregas lógica de edición
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            ) {
                Text("Editar")
            }

            Button(
                onClick = {
                    auth.signOut()
                    rootNavController.navigate("initial") {
                        popUpTo(0) { inclusive = true } // limpia toda la pila
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Cerrar Sesión")
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    //ProfileScreen()
}

