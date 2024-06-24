package com.app.majuapp.component

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
    ),
    favoriteButtonFlag: Boolean = true,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .wrapContentHeight(align = Alignment.Top)
            .clickable { onClick() }
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
            NetworkImageCard(
                networkUrl = culture.sumnailUrl,
                modifier = Modifier
                    .width(120.dp),
            )
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
                    CultureDetailCategoryChip(cultureDetailCategory = culture.category)
                    if (favoriteButtonFlag)
                        Icon(
                            imageVector = Icons.Outlined.FavoriteBorder,
                            contentDescription = "favorite"
                        )
                }
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    text = culture.title,
                    textAlign = TextAlign.Center,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.weight(1f))

                SingleLineTextWithIconOnStart(
                    textContent = culture.location,
                    iconDescription = "location",
                    imageVector = Icons.Outlined.LocationOn,
                    iconTint = HighlightColor,
                    size = 14,
                    intervalSize = 4.dp
                )
                SingleLineTextWithIconOnStart(
                    textContent = culture.time,
                    iconDescription = "time",
                    imageVector = Icons.Outlined.AccessTime,
                    iconTint = HighlightColor,
                    size = 14,
                    intervalSize = 4.dp
                )
            } // Item 2
        }
    }
} // End of CultureCard


@Preview
@Composable
fun PreviewCultureCard() {
    CultureCard(CultureModel(
        0,
        "뮤지컬/오페라",
        "https://cdn.woman.chosun.com/news/photo/202309/112221_118277_4824.jpg",
        "오페라 갈라",
        "세종 대극장",
        "2024-12-07"
    ))
} // End of PreviewCultureCard