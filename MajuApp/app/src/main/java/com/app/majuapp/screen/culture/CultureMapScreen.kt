package com.app.majuapp.screen.culture

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.app.majuapp.R
import com.app.majuapp.component.CultureCard
import com.app.majuapp.component.MapMarker
import com.app.majuapp.component.RowChoiceChips
import com.app.majuapp.ui.theme.cultureDefaultPadding
import com.app.majuapp.util.dummyCultureCategories
import com.app.majuapp.util.dummyList
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState

@Composable
fun CultureMapScreen(navController: NavHostController = rememberNavController()) {

    Surface(
        modifier = Modifier
            .fillMaxSize()
//            .padding(top = 40.dp, start = 24.dp, end = 24.dp)
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            GoogleMap(
                modifier = Modifier
                    .fillMaxSize(),

                ) {
                MapMarker(
                    position = LatLng(0.0, 0.0),
                    title = "",
                    snippet = "Marker",
                    iconResourceId = R.drawable.ic_pin_music
                )
            }

            RowChoiceChips(
                dummyCultureCategories,
                Modifier
                    .align(Alignment.TopStart)
                    .padding(start = cultureDefaultPadding, end = cultureDefaultPadding)
                    .offset(y = cultureDefaultPadding),
            )

            CultureCard(
                dummyList[0],
                false,
                modifier = Modifier
                    .wrapContentSize()
                    .align(Alignment.BottomCenter)
                    .padding(start = cultureDefaultPadding, end = cultureDefaultPadding)
                    .offset(y = -cultureDefaultPadding),
            )
        }


    }

} // End of CultureMapScreen

@Preview
@Composable
fun PreviewCultureMapScreen() {
    CultureMapScreen()
} // End of PreviewCultureMapScreen