package com.app.majuapp.component.culture

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.majuapp.ui.theme.SpiroDiscoBall

@Composable
fun CultureDetailCategoryChip(cultureDetailCategory: String) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .background(SpiroDiscoBall)
            .padding(vertical = 4.dp, horizontal = 6.dp),

        ) {
        Text(
            text = cultureDetailCategory,
            fontWeight = FontWeight.SemiBold,
            style = TextStyle(color = Color.White),
        )
    }
} // End of CultureDetailCategoryChip

@Preview
@Composable
fun PreviewCultureDetailCategoryChip() {
    CultureDetailCategoryChip("뮤지컬/오페라")
} // End of PreviewCultureDetailCategoryChip