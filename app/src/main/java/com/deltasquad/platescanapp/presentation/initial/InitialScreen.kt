package com.deltasquad.platescanapp.presentation.initial

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.deltasquad.platescanapp.R
import com.deltasquad.platescanapp.presentation.theme.*

@Composable
fun InitialScreen(
    navigateToLogin: () -> Unit = {},
    navigateToSignUp: () -> Unit = {},
    onGoogleSignInClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(secondaryGray, secondaryBlack), startY = 0f, endY = 220f)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(id = R.drawable.logo_initial),
            contentDescription = "Logo",
            modifier = Modifier
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            "Sign up for free",
            color = Color.White,
            fontSize = 38.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            "And scans plates",
            color = Color.White,
            fontSize = 38.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = { navigateToSignUp() },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .padding(horizontal = 32.dp)
                .border(2.dp, secondaryGreen, CircleShape),
            colors = ButtonDefaults.buttonColors(containerColor = primaryGreen)
        ) {
            Text(text = "Sign up free", color = primaryWhite, fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.height(8.dp))

        // Aquí llamamos a onGoogleSignInClick
        CustomButton(
            modifier = Modifier.clickable { onGoogleSignInClick() },
            painter = painterResource(id = R.drawable.google),
            title = "Continue with Google"
        )

        Spacer(modifier = Modifier.height(8.dp))
        CustomButton(
            modifier = Modifier.clickable { /* pendiente facebook */ },
            painter = painterResource(id = R.drawable.facebook),
            title = "Continue with Facebook"
        )
        Text(
            text = "Log in",
            color = primaryWhite,
            modifier = Modifier
                .padding(24.dp)
                .clickable { navigateToLogin() },
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}


@Composable
fun CustomButton(modifier: Modifier, painter: Painter, title: String){
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .padding(horizontal = 32.dp)
            .background(secondaryBlack)
            .border(2.dp, primaryWhite, CircleShape),
        contentAlignment = Alignment.CenterStart
    ) {
        Image(
            painter = painter,
            contentDescription = "",
            modifier = Modifier
                .padding(start = 16.dp)
                .size(16.dp)
        )
        Text(
            text = title,
            color = Color.White,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview(showBackground = true)
@Composable
fun InitialPreview() {
    PlateScanAppTheme {
        //InitialScreen()
    }
}

