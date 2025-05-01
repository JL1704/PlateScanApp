package com.deltasquad.platescanapp.presentation.login

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.deltasquad.platescanapp.R
import com.deltasquad.platescanapp.presentation.theme.SelectedField
import com.deltasquad.platescanapp.presentation.theme.UnSelectedField
import com.deltasquad.platescanapp.presentation.theme.primaryGreen
import com.deltasquad.platescanapp.presentation.theme.secondaryGreen
import com.google.firebase.auth.FirebaseAuth

@Composable
fun LoginScreen(
    auth: FirebaseAuth,
    navController: NavHostController,
    onLoginSuccess: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Mostrar snackbar cuando haya error
    LaunchedEffect(errorMessage) {
        errorMessage?.let {
            snackbarHostState.showSnackbar(it)
            errorMessage = null // Limpiar el mensaje para que no se vuelva a mostrar
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Black)
                .padding(horizontal = 24.dp)
                .padding(top = 32.dp)
                .padding(innerPadding),
            horizontalAlignment = Alignment.Start
        ) {
            // Back y título
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 32.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back_24),
                    contentDescription = "Back",
                    tint = White,
                    modifier = Modifier
                        .size(28.dp)
                        .clickable {
                            navController.popBackStack()
                        }
                )
                Text(
                    text = "Log In",
                    color = White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 28.dp),
                    textAlign = TextAlign.Center
                )
            }

            // Email
            Text("Email", color = White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            TextField(
                value = email,
                onValueChange = { email = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_mail),
                        contentDescription = "Email Icon",
                        tint = White
                    )
                },
                singleLine = true,
                textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Start),
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = UnSelectedField,
                    focusedContainerColor = SelectedField,
                    focusedTextColor = White,
                    unfocusedTextColor = White,
                    cursorColor = White
                )
            )

            // Password
            Text("Password", color = White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            TextField(
                value = password,
                onValueChange = { password = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_lock),
                        contentDescription = "Password Icon",
                        tint = White
                    )
                },
                trailingIcon = {
                    IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_eye),
                            contentDescription = "Toggle Password Visibility",
                            tint = White
                        )
                    }
                },
                visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                singleLine = true,
                textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Start),
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = UnSelectedField,
                    focusedContainerColor = SelectedField,
                    focusedTextColor = White,
                    unfocusedTextColor = White,
                    cursorColor = White
                )
            )

            Spacer(modifier = Modifier.weight(1f))

            // Botón Login
            Button(
                onClick = {
                    if (email.isBlank() || password.isBlank()) {
                        errorMessage = "Please complete all fields"
                    } else {
                        auth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Log.i("Luis", "Login Ok")
                                    onLoginSuccess()
                                } else {
                                    val message = task.exception?.localizedMessage ?: "Login failed"
                                    Log.e("Luis", "Login KO: $message")
                                    errorMessage = message
                                }
                            }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 60.dp)
                    .height(56.dp)
                    .border(2.dp, secondaryGreen, RoundedCornerShape(16.dp)),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = primaryGreen)
            ) {
                Text("Login", fontSize = 18.sp)
            }
        }
    }
}
