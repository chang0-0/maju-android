package com.app.majuapp.screen.culture

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.app.majuapp.R
import com.app.majuapp.component.CultureCard
import com.app.majuapp.component.MapMarker
import com.app.majuapp.component.RowChoiceChips
import com.app.majuapp.ui.theme.cultureDefaultPadding
import com.app.majuapp.util.checkAndRequestPermissions
import com.app.majuapp.util.dummyCultureCategories
import com.app.majuapp.util.dummyList
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun CultureMapScreen(
    navController: NavHostController = rememberNavController(),
    cultureViewModel: CultureViewModel
) {

    val context = LocalContext.current
    val currentLocation = cultureViewModel.currentLocation.collectAsStateWithLifecycle()

    /** 요청할 권한 **/
    val permissions = arrayOf(
        android.Manifest.permission.ACCESS_COARSE_LOCATION,
        android.Manifest.permission.ACCESS_FINE_LOCATION
    )

    val launcherMultiplePermissions = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissionsMap ->
        val areGranted = permissionsMap.values.fold(true) { acc, next -> acc && next }
        /** 권한 요청시 동의 했을 경우 **/
        if (areGranted) {
            Log.d("permission", "권한이 동의되었습니다.")
            cultureViewModel.getCurrentLocation()
        }
        /** 권한 요청시 거부 했을 경우 **/
        else {
            Log.d("permission", "권한이 거부되었습니다.")
            navController.popBackStack()
        }
    }

    var latLng = LatLng(37.7387295, 127.0458908)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            latLng, 15f
        )
    }

    LaunchedEffect(Unit) {
        checkAndRequestPermissions(
            context,
            permissions,
            launcherMultiplePermissions
        )
    }

    LaunchedEffect(key1 = currentLocation.value) {
        Log.d("currentPosition", "${currentLocation.value?.latitude}, ${currentLocation.value?.longitude}")
        currentLocation.value?.let { location ->
            cameraPositionState.position =
                CameraPosition.fromLatLngZoom(
                    LatLng(location.latitude, location.longitude), 15f
                )
        }
    }
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
                cameraPositionState = cameraPositionState,
            ) {
                MapMarker(
                    position = latLng,
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
//    CultureMapScreen()
} // End of PreviewCultureMapScreen