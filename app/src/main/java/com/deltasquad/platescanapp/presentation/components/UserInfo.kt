package com.deltasquad.platescanapp.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.font.FontWeight
import com.deltasquad.platescanapp.presentation.theme.PlateScanAppTheme

@Composable
fun UserInfo(
    username: String = "example1234",
    email: String = "user@example.com",
    phone: String = "+1 123 456 7890",
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        InfoCard(label = "Username", value = username)
        Spacer(modifier = Modifier.height(12.dp))
        InfoCard(label = "Email", value = email)
        Spacer(modifier = Modifier.height(12.dp))
        InfoCard(label = "Phone", value = phone)
    }
}

@Composable
fun InfoCard(label: String, value: String) {
    val textColor = MaterialTheme.colorScheme.onSurface

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                color = textColor.copy(alpha = 0.7f),
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium),
                color = textColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UserInfoPreview() {
    MaterialTheme {
        UserInfo()
    }
}
