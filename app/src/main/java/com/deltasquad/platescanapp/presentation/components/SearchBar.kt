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
        placeholder = { Text(text = "Search...", fontSize = 16.sp) },
        shape = RoundedCornerShape(PlateScanAppTheme.dimens.roundedShapeMedium),
        modifier = modifier
            .fillMaxWidth()
            .padding(PlateScanAppTheme.dimens.paddingMedium),
        singleLine = true,
        textStyle = TextStyle(textAlign = TextAlign.Start)
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

