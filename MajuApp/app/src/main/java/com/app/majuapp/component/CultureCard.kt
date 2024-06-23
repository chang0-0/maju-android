package com.app.majuapp.component

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.app.majuapp.data.model.CultureModel
import com.app.majuapp.ui.theme.CategoryBackgroundColor
import com.app.majuapp.ui.theme.HighlightColor

@Composable
fun CultureCard(
    culture: CultureModel = CultureModel(
        0,
        "뮤지컬/오페라",
        "https://cdn.woman.chosun.com/news/photo/202309/112221_118277_4824.jpg",
        "오페라 갈라",
        "세종 대극장",
        "2024-12-07"
    )
) {
    Card(
        modifier = Modifier
            .wrapContentHeight(align = Alignment.Top)
            .fillMaxWidth()
            .border(
                width = 3.dp,
                color = Color.LightGray,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Row(
            modifier = Modifier.height(IntrinsicSize.Min),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Card(
                modifier = Modifier
                    .width(120.dp)
                    .aspectRatio(1f),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent)
            ) {
                val context = LocalContext.current
                SubcomposeAsyncImage(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(16.dp)),
                    model = ImageRequest.Builder(context)
                        .data(culture.sumnailUrl)
                        .crossfade(true).build(),
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.TopCenter,
                    contentDescription = "",
                )
            } // Item 1
            Box(
                modifier = Modifier
                    .width(16.dp)
            )
            Column(
                modifier = Modifier
                    .weight(2f),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(CategoryBackgroundColor)
                            .padding(4.dp),

                        ) {
                        Text(
                            text = culture.category,
                            style = TextStyle(color = Color.White),
                        )
                    }
                    Icon(
                        imageVector = Icons.Outlined.FavoriteBorder,
                        contentDescription = "favorite"
                    )
                }
                Spacer(modifier = Modifier.weight(1f)) // this is required to push below composables to bottom
                Text(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    text = culture.title,
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier = Modifier.weight(1f)) // this is required to push below composables to bottom
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Outlined.LocationOn,
                        contentDescription = "location",
                        modifier = Modifier.size(16.dp),
                        tint = HighlightColor
                    )
                    Text(culture.location, fontSize = 14.sp)
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Outlined.AccessTime,
                        contentDescription = "time",
                        modifier = Modifier.size(16.dp),
                        tint = HighlightColor
                    )
                    Text(culture.time, fontSize = 14.sp)
                }
            } // Item 2
        }
    }
}


@Preview
@Composable
fun PreviewPreferenceScreen() {
    CultureCard()
} // End of PreviewPreferenceScreen