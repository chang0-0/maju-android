package com.app.majuapp.component

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.app.majuapp.util.bitmapDescriptorUsingVector
import com.app.majuapp.util.findActivity
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState

@Composable
fun MapMarker(
    position: LatLng,
    title: String,
    snippet: String,
    @DrawableRes iconResourceId: Int,
    onClick: () -> Boolean = { false }
) {
    val context = LocalContext.current.findActivity()
    val icon = bitmapDescriptorUsingVector(
        context, iconResourceId
    )
    Marker(
        state = MarkerState(
            position = position
        ),
        title = title,
        snippet = snippet,
        icon = icon,
        onClick = {
            onClick()
        }
    )
}
