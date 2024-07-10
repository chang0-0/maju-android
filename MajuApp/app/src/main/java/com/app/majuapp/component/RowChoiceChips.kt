package com.app.majuapp.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.majuapp.ui.theme.GoldenPoppy
import com.app.majuapp.ui.theme.OldSilver
import com.app.majuapp.util.dummyCultureCategories

@Composable
fun RowChoiceChips(
    itemList: List<String>,
    modifier: Modifier = Modifier
) {
    var choicedItemIdx by remember { mutableStateOf(-1) }

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
        itemsIndexed(itemList) { idx, item ->
            FilterChip(
                selected = choicedItemIdx == idx,
                onClick = {
                    choicedItemIdx = if (choicedItemIdx == idx) -1 else idx
                },
                label = {
                    Text(
                        fontWeight = FontWeight.Medium,
                        fontSize = 18.sp,
                        text = "$item",
                        textAlign = TextAlign.Center,
                        maxLines = 1
                    )
                },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = GoldenPoppy,
                    selectedLabelColor = Color.White,
                    disabledLabelColor = OldSilver,
                    containerColor = Color.White
                ),
                border = FilterChipDefaults.filterChipBorder(
                    enabled = true,
                    selected = choicedItemIdx == idx,
                    selectedBorderColor = Color.Transparent,
                    borderColor = Color.LightGray
                ),
            )
        }
    }

} // End of ChoiceChips

@Preview
@Composable
fun PreviewChoiceChips() {
    RowChoiceChips(dummyCultureCategories)
}