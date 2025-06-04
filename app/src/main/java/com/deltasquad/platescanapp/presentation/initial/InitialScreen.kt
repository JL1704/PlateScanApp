package com.deltasquad.platescanapp.presentation.initial

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.deltasquad.platescanapp.R
import com.deltasquad.platescanapp.presentation.components.CustomButton
import com.deltasquad.platescanapp.presentation.theme.*

@Composable
fun InitialScreen(
    navigateToLogin: () -> Unit = {},         // Callback para navegar a la pantalla de login
    navigateToSignUp: () -> Unit = {},        // Callback para navegar a la pantalla de registro
    onGoogleSignInClick: () -> Unit           // Callback para iniciar sesión con Google
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background( // Fondo con gradiente vertical desde gris a negro
                Brush.verticalGradient(
                    colors = listOf(secondaryGray, secondaryBlack),
                    startY = 0f,
                    endY = 220f
                )
            ),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp)) // Espacio superior reducido

        // Logo de la aplicación
        Image(
            painter = painterResource(id = R.drawable.logo_initial),
            contentDescription = "App Logo",
            modifier = Modifier.size(180.dp)
        )

        // Textos principales: slogan y llamada a la acción
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Scan license plates",
                color = Color.White,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "in seconds",
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Sign up for free",
                color = secondaryGreen,
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        // Botones de interacción
        Column(
            modifier = Modifier.padding(bottom = 96.dp), // Espacio inferior adicional
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Botón de registro
            Button(
                onClick = navigateToSignUp,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .padding(horizontal = 32.dp),
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(containerColor = primaryGreen)
            ) {
                Text(
                    text = "Sign up free",
                    color = primaryWhite,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Botón personalizado para continuar con Google
            CustomButton(
                painter = painterResource(id = R.drawable.google),
                title = "Continue with Google",
                onClick = onGoogleSignInClick
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Texto clickeable para navegar al login
            Text(
                text = "Log in",
                color = primaryWhite,
                modifier = Modifier
                    .padding(top = 16.dp)
                    .clickable { navigateToLogin() },
                fontWeight = FontWeight.Bold
            )
        }
    }
}
