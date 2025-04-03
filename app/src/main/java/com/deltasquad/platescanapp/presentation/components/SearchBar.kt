package com.deltasquad.platescanapp.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.TextStyle
import com.deltasquad.platescanapp.presentation.theme.PlateScanAppTheme
import androidx.compose.ui.text.style.TextAlign

@Composable
fun SearchBar(
    query: String,
    onQueryChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = query,
        onValueChange = onQueryChanged,
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon")
        },
        placeholder = { Text(text = "Buscar...", fontSize = 16.sp) },
        shape = RoundedCornerShape(12.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        singleLine = true,
        textStyle = TextStyle(textAlign = TextAlign.Start) // Aquí alineamos el texto a la izquierda
    )
}

@Preview(showBackground = true)
@Composable
fun SearchBarPreview() {
    PlateScanAppTheme {
        var text by remember { mutableStateOf("") }
        SearchBar(query = text, onQueryChanged = { text = it })
    }
}

