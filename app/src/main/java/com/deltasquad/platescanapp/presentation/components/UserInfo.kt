package com.deltasquad.platescanapp.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun UserInfo(
    username: String = "example1234",
    email: String = "user@example.com",
    phone: String = "+1 123 456 7890",
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(16.dp)) {
        LabelValue(
            label = "Username",
            value = username
        )
        Spacer(modifier = Modifier.height(16.dp))

        LabelValue(
            label = "Email",
            value = email
        )
        Spacer(modifier = Modifier.height(16.dp))

        LabelValue(
            label = "Phone",
            value = phone
        )
    }
}

@Preview(showBackground = true)
@Composable
fun UserInfoPreview() {
    UserInfo()
}
