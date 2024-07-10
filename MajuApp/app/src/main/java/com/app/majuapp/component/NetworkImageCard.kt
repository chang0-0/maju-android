package com.app.majuapp.component

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest

@Composable
fun NetworkImageCard(networkUrl: String, modifier: Modifier = Modifier,) {
    Card(
        modifier = modifier
            .aspectRatio(1f),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        val context = LocalContext.current
        SubcomposeAsyncImage(
            modifier = Modifier
                .fillMaxSize()
                .clip(CardDefaults.shape),
            model = ImageRequest.Builder(context)
                .data(networkUrl)
                .crossfade(true).build(),
            contentScale = ContentScale.Crop,
            alignment = Alignment.TopCenter,
            contentDescription = "",
        )
    }
} // End of NetworkImageCard


@Preview
@Composable
fun PreviewNetworkImageCard() {
    NetworkImageCard("https://cdn.woman.chosun.com/news/photo/202309/112221_118277_4824.jpg")
} // End of PreviewNetworkImageCard