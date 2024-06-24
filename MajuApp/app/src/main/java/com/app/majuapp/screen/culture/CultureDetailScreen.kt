package com.app.majuapp.screen.culture

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.MonetizationOn
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.app.majuapp.component.CultureCard
import com.app.majuapp.component.CultureDetailCategoryChip
import com.app.majuapp.component.MultiLineTextWithIconOnStart
import com.app.majuapp.component.NetworkImageCard
import com.app.majuapp.component.SingleLineTextWithIconOnStart
import com.app.majuapp.data.model.CultureModel
import com.app.majuapp.ui.theme.HighlightColor
import com.app.majuapp.util.dummyList
import com.app.majuapp.util.textCenterAlignment

@Composable
fun CultureDetailScreen(navController: NavHostController) {

    //TODO Change REAL DATA
    val culture = dummyList[0]

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 40.dp, start = 24.dp, end = 24.dp)
    ) {
        Column(
            modifier = Modifier
        ) {
            NetworkImageCard(networkUrl = culture.sumnailUrl, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CultureDetailCategoryChip(cultureDetailCategory = culture.category)
                    Icon(
                        imageVector = Icons.Outlined.FavoriteBorder,
                        contentDescription = "favorite"
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 24.sp,
                    text = culture.title,
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier = Modifier.height(16.dp))
                SingleLineTextWithIconOnStart(
                    textContent = culture.location,
                    iconDescription = "location",
                    imageVector = Icons.Outlined.LocationOn,
                    iconTint = HighlightColor,
                    size = 18,
                    intervalSize = 4.dp
                )
                Spacer(modifier = Modifier.height(8.dp))
                SingleLineTextWithIconOnStart(
                    textContent = culture.time,
                    iconDescription = "time",
                    imageVector = Icons.Outlined.AccessTime,
                    iconTint = HighlightColor,
                    size = 18,
                    intervalSize = 4.dp
                )
                Spacer(modifier = Modifier.height(8.dp))
                MultiLineTextWithIconOnStart(
                    textContent = culture.moneyInfo,
                    iconDescription = "moneyInformation",
                    imageVector = Icons.Outlined.MonetizationOn,
                    iconTint = HighlightColor,
                    size = 18,
                    intervalSize = 4.dp
                )
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.padding(vertical = 16.dp)
            ) {
                Button(onClick = { /*TODO*/ }, Modifier.weight(1f)) {
                    Text("뒤로 가기")
                }

                Button(onClick = { /*TODO*/ }, Modifier.weight(1f)) {
                    Text("홈페이지")
                }
            }
        }
    }

} // End of CultureDetailScreen


@Preview
@Composable
fun PreviewCultureDetailScreen() {
    CultureDetailScreen(rememberNavController())
} // End of PreviewPreferenceScreen