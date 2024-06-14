package com.app.majuapp.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.app.majuapp.screen.home.Category
import com.app.majuapp.ui.theme.roundedCornerPadding

@Composable
fun HomeScreenRoundedCard(modifier: Modifier, color: List<Color>, content: @Composable () -> Unit) {
    Box(
        modifier = modifier.clip(RoundedCornerShape(roundedCornerPadding)).fillMaxWidth()
            .height(200.dp).background(
                Brush.linearGradient(
                    colors = listOf(color[0], color[1]),
                    start = Offset(0f, 0f),
                    end = Offset(800f, 800f)
                ),
            )
    ) {
        content()
    }
} // End of HomeBox()

@Composable
fun HomeScreenCategoryItem(categoryItem: Category, onClick: (String) -> Unit) {
    Box(
        modifier = Modifier
            .aspectRatio(1f),
        contentAlignment = Alignment.Center
    ) {
        SubcomposeAsyncImage(modifier = Modifier.clip(RoundedCornerShape(12.dp))
            .clickable {
                onClick("")
            },
            model = ImageRequest.Builder(LocalContext.current).data(categoryItem.imageUrl)
                .crossfade(true).build(),
            contentDescription = categoryItem.imageUrl,
            contentScale = ContentScale.Crop,
            loading = {
                Loader()
            })
        Text(
            fontWeight = FontWeight.Bold,
            fontSize = 38.sp,
            text = categoryItem.title,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.Center)
        )
    }
} // End of HomeScreenCategoryCard()

@Composable
fun HomeScreenSpacer() {
    Spacer(modifier = Modifier.height(40.dp))
} // End of HomeScreenSpacer()
