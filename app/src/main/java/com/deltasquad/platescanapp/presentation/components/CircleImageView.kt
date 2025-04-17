package com.deltasquad.platescanapp.presentation.components


import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.deltasquad.platescanapp.R
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource

@Composable
fun CircleImageView(
    imageUrl: String?,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    AsyncImage(
        model = ImageRequest.Builder(context)
            .data(imageUrl)
            .crossfade(true)
            .build(),
        contentDescription = "Profile Picture",
        modifier = modifier
            .size(80.dp)
            .clip(CircleShape),
        placeholder = painterResource(id = R.drawable.default_profile),
        error = painterResource(id = R.drawable.default_profile)
    )
}

@Preview(showBackground = true)
@Composable
fun CircleImagePreview() {
    CircleImageView(imageUrl = "https://example.com/profile.jpg")
}
