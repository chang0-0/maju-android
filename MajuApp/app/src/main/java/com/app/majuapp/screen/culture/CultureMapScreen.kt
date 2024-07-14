package com.app.majuapp.screen.culture

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.app.majuapp.R
import com.app.majuapp.component.MapMarker
import com.app.majuapp.component.culture.CultureCard
import com.app.majuapp.component.culture.CultureRowChoiceChips
import com.app.majuapp.data.dto.NetworkDto
import com.app.majuapp.domain.model.CultureEventDomainModel
import com.app.majuapp.ui.theme.cultureDefaultPadding
import com.app.majuapp.util.NetworkResult
import com.app.majuapp.util.checkAndRequestPermissions
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.DefaultMapUiSettings
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun CultureMapScreen(
    navController: NavHostController = rememberNavController(),
    cultureViewModel: CultureViewModel,
    navigateToCultureScreen: () -> Unit,
    onBack: () -> Unit
) {

    val context = LocalContext.current
    val currentLocation = cultureViewModel.currentLocation.collectAsStateWithLifecycle()
    val cultureEventListNetworkResult =
        cultureViewModel.cultureEventListNetworkResult.collectAsStateWithLifecycle()
    val cultureEventToggleNetworkResult =
        cultureViewModel.cultureEventToggleNetworkResult.collectAsStateWithLifecycle()
    val cultureEventList = cultureViewModel.cultureEventList.collectAsStateWithLifecycle()
    val focusedEvent = cultureViewModel.focusedEvent.collectAsStateWithLifecycle()

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
            onBack()
        }
    }

    BackHandler {
        onBack()
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
        Log.d(
            "currentPosition",
            "${currentLocation.value?.latitude}, ${currentLocation.value?.longitude}"
        )
        currentLocation.value?.let { location ->
            cameraPositionState.position =
                CameraPosition.fromLatLngZoom(
                    LatLng(location.latitude, location.longitude), 15f
                )
        }
        cultureViewModel.getAllCultureEvents()
    }

    LaunchedEffect(key1 = cultureEventListNetworkResult.value) {
        cultureEventListNetworkResult.value.let {
            when (it) {
                is NetworkResult.Error -> {
                    Log.e("culture Api", it.msg ?: "에러")
                }

                is NetworkResult.Idle -> {
                    Log.d("culture Api", "IDLE")
                }

                is NetworkResult.Loading -> {
                    Log.d("culture Api", "LOADING")
                }

                is NetworkResult.Success -> {
                    if (it.value.status == 200) {
                        cultureViewModel.unfocusEvent()
                    } else {
                        Log.e("culture Api", "culture events api call failed.")
                    }
                }
            }
        }
    }

    LaunchedEffect(key1 = cultureEventListNetworkResult.value) {
        cultureEventListNetworkResult.value.let {
            when (it) {
                is NetworkResult.Error -> {
                    Log.e("culture Api", it.msg ?: "에러")
                }

                is NetworkResult.Idle -> {
                    Log.d("culture Api", "IDLE")
                }

                is NetworkResult.Loading -> {
                    Log.d("culture Api", "LOADING")
                }

                is NetworkResult.Success -> {
                    if (it.value.status == 201) {
                        cultureEventList.value.find { it.id == cultureViewModel.focusedEvent.value!!.id }?.let { event ->
                            cultureViewModel.focusEvent(event.copy())
                        }
                    } else {
                        Log.e("culture Api", "culture events api call failed.")
                    }
                }
            }
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
                uiSettings = DefaultMapUiSettings.copy(
                    compassEnabled = false,
                    zoomControlsEnabled = false
                ),
                onMapClick = { latLng ->
                    cultureViewModel.unfocusEvent()
                }
            ) {
                for (cultureEvent in cultureEventList.value) {
                    MapMarker(
                        position = LatLng(
                            cultureEvent.lat,
                            cultureEvent.lon
                        ),
                        title = cultureEvent.eventName,
                        snippet = "Marker",
                        iconResourceId = when (cultureEvent.genre) {
                            "음악" -> R.drawable.ic_pin_music
                            "전시" -> R.drawable.ic_pin_exhibition
                            "연극" -> R.drawable.ic_pin_theater
                            "체험" -> R.drawable.ic_pin_experience
                            else -> R.drawable.ic_pin_experience
                        }
                    ) {
                        cultureViewModel.focusEvent(cultureEvent)
                        false
                    }
                }
            }

            CultureRowChoiceChips(
                cultureViewModel,
                Modifier
                    .align(Alignment.TopStart)
                    .padding(start = cultureDefaultPadding, end = cultureDefaultPadding)
                    .offset(y = cultureDefaultPadding),
            )

            if (focusedEvent.value != null)
                CultureCard(
                    focusedEvent.value!!,
                    true,
                    modifier = Modifier
                        .wrapContentSize()
                        .align(Alignment.BottomCenter)
                        .padding(start = cultureDefaultPadding, end = cultureDefaultPadding)
                        .offset(y = -cultureDefaultPadding),
                    onLikeClicked = { id ->
                        cultureViewModel.toggleCultureLike(id)
                    }
                )
        }
    }

} // End of CultureMapScreen

@Preview
@Composable
fun PreviewCultureMapScreen() {
//    CultureMapScreen()
} // End of PreviewCultureMapScreen