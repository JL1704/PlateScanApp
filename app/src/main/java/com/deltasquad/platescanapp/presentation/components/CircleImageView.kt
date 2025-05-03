package com.deltasquad.platescanapp.presentation.components

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.deltasquad.platescanapp.R

@Composable
fun CircleImageView(
    imageUrl: String?, // Puede ser URL remota o file:// o content://
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    val imageRequest = ImageRequest.Builder(context)
        .data(imageUrl)
        .crossfade(true)
        .build()

    AsyncImage(
        model = imageRequest,
        contentDescription = "Profile Picture",
        modifier = modifier
            .size(120.dp)
            .clip(CircleShape),
        placeholder = painterResource(id = R.drawable.default_profile),
        error = painterResource(id = R.drawable.default_profile)
    )
}

