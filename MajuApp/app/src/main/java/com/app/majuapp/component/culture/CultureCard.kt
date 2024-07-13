package com.app.majuapp.component.culture

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.majuapp.component.NetworkImageCard
import com.app.majuapp.component.SingleLineTextWithIconOnStart
import com.app.majuapp.domain.model.CultureEventDomainModel
import com.app.majuapp.ui.theme.GoldenPoppy

@Composable
fun CultureCard(
    culture: CultureEventDomainModel,
    favoriteButtonFlag: Boolean = true,
    modifier: Modifier = Modifier,
    onClick: (Int) -> Unit = {},
) {
    Card(
        modifier = modifier
            .wrapContentHeight(align = Alignment.Top)
            .clickable { onClick(culture.id) }
            .fillMaxWidth()
            .border(
                width = 3.dp,
                color = Color.LightGray,
                shape = CardDefaults.shape
            ),
        colors = CardDefaults.cardColors(containerColor = Color.White, )
    ) {
        Row(
            modifier = Modifier.height(IntrinsicSize.Min)
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            NetworkImageCard(
                networkUrl = culture.thumbnail,
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
                    text = culture.eventName,
                    textAlign = TextAlign.Center,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.weight(1f))

                SingleLineTextWithIconOnStart(
                    textContent = culture.place,
                    iconDescription = "location",
                    imageVector = Icons.Outlined.LocationOn,
                    iconTint = GoldenPoppy,
                    size = 14,
                    intervalSize = 4.dp
                )
                SingleLineTextWithIconOnStart(
                    textContent = culture.startDate,
                    iconDescription = "time",
                    imageVector = Icons.Outlined.AccessTime,
                    iconTint = GoldenPoppy,
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
//    CultureCard()
} // End of PreviewCultureCard