package com.app.majuapp.screen.walk

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.app.majuapp.R
import com.app.majuapp.component.Loader
import com.app.majuapp.component.walk.WalkRecordingBox
import com.app.majuapp.component.walk.WalkScreenChooseStartDialog
import com.app.majuapp.component.walk.WalkScreenInformDialogue
import com.app.majuapp.domain.model.walk.CoordinateData
import com.app.majuapp.ui.theme.SpiroDiscoBall
import com.app.majuapp.ui.theme.White
import com.app.majuapp.ui.theme.defaultPadding
import com.app.majuapp.ui.theme.notoSansKoreanFontFamily
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.launch

private const val TAG = "WalkScreen_창영"

private val permissionsToRequest = arrayOf(
    Manifest.permission.ACCESS_FINE_LOCATION,
    Manifest.permission.ACCESS_COARSE_LOCATION,
)

private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun WalkScreen(navController: NavController, walkViewModel: WalkViewModel = hiltViewModel()) {
    // 사용자 위치 권한 가져오기
    // Request Permission
    // context
    val context = LocalContext.current

    val dialogQueue = walkViewModel.visiblePermissionDialogQueue

    val permissionResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            walkViewModel.onPermissionResult(
                permission = Manifest.permission.CAMERA, isGranted = isGranted
            )
        })

    val multiplePermissionResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { perms ->
            permissionsToRequest.forEach { permission ->
                walkViewModel.onPermissionResult(
                    permission = permission, isGranted = perms[permission] == true
                )
            }
        })


    Surface(
        modifier = Modifier.fillMaxSize(),
    ) {
        var locationText by remember { mutableStateOf("No location obtained :(") }
        var showPermissionResultText by remember { mutableStateOf(false) }
        var permissionResultText by remember { mutableStateOf("Permission Granted...") }

        RequestLocationPermission(
            onPermissionGranted = {
                // Callback when permission is granted
                showPermissionResultText = true
                // Attempt to get the last known user location
                getLastUserLocation(
                    context,
                    onGetLastLocationSuccess = {
                        locationText =
                            "Location using LAST-LOCATION: LATITUDE: ${it.first}, LONGITUDE: ${it.second}"
                    },
                    onGetLastLocationFailed = { exception ->
                        showPermissionResultText = true
                        locationText =
                            exception.localizedMessage ?: "Error Getting Last Location"
                    },
                    onGetLastLocationIsNull = {
                        // Attempt to get the current user location
                        getCurrentLocation(
                            context,
                            onGetCurrentLocationSuccess = {
                                locationText =
                                    "Location using CURRENT-LOCATION: LATITUDE: ${it.lat}, LONGITUDE: ${it.lng}"
                            },
                            onGetCurrentLocationFailed = {
                                showPermissionResultText = true
                                locationText =
                                    it.localizedMessage
                                        ?: "Error Getting Current Location"
                            }
                        )
                    }
                )
            },
            onPermissionDenied = {
                // Callback when permission is denied
                showPermissionResultText = true
                permissionResultText = "Permission Denied :("
            },
            onPermissionsRevoked = {
                // Callback when permission is revoked
                showPermissionResultText = true
                permissionResultText = "Permission Revoked :("
            }
        )

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Display a message indicating the permission request process
            Text(
                text = "Requesting location permission...",
                textAlign = TextAlign.Center
            )

            // Display permission result and location information if available
            if (showPermissionResultText) {
                Text(text = permissionResultText, textAlign = TextAlign.Center)
                Text(text = locationText, textAlign = TextAlign.Center)
            }
        }
    }


    // WalkScreenContent(navController, walkViewModel)
} // End of WalkScreen()

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestLocationPermission(
    onPermissionGranted: () -> Unit,
    onPermissionDenied: () -> Unit,
    onPermissionsRevoked: () -> Unit
) {
    // Initialize the state for managing multiple location permissions.
    val permissionState = rememberMultiplePermissionsState(
        listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
        )
    )

    // Use LaunchedEffect to handle permissions logic when the composition is launched.
    LaunchedEffect(key1 = permissionState) {
        // Check if all previously granted permissions are revoked.
        val allPermissionsRevoked =
            permissionState.permissions.size == permissionState.revokedPermissions.size

        // Filter permissions that need to be requested.
        val permissionsToRequest = permissionState.permissions.filter {
            !it.status.isGranted
        }

        // If there are permissions to request, launch the permission request.
        if (permissionsToRequest.isNotEmpty()) permissionState.launchMultiplePermissionRequest()

        // Execute callbacks based on permission status.
        if (allPermissionsRevoked) {
            onPermissionsRevoked()
        } else {
            if (permissionState.allPermissionsGranted) {
                onPermissionGranted()
            } else {
                onPermissionDenied()
            }
        }
    }
} // End of RequestLocationPermission()


// https://www.youtube.com/watch?v=f7YLwr4oUFE
@SuppressLint("MissingPermission")
private fun getCurrentLocation(
    context: Context,
    onGetCurrentLocationSuccess: (CoordinateData) -> Unit,
    onGetCurrentLocationFailed: (Exception) -> Unit,
    priority: Boolean = true
) {
    val accuracy = if (priority) {
        Priority.PRIORITY_HIGH_ACCURACY
    } else {
        Priority.PRIORITY_BALANCED_POWER_ACCURACY
    }

    if (areLocationPermissionsGranted(context)) {
        fusedLocationProviderClient.getCurrentLocation(
            accuracy, CancellationTokenSource().token,
        ).addOnSuccessListener { location ->
            location?.let {
                onGetCurrentLocationSuccess(CoordinateData(it.latitude, it.longitude))
            }?.run {
                // Location null do something
            }
        }.addOnFailureListener { exception ->
            onGetCurrentLocationFailed(exception)
        }
    }
} // End of getCurrentLocation()

@SuppressLint("MissingPermission")
private fun getLastUserLocation(
    context: Context,
    onGetLastLocationSuccess: (Pair<Double, Double>) -> Unit,
    onGetLastLocationFailed: (Exception) -> Unit,
    onGetLastLocationIsNull: () -> Unit
) {
    fusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)
    // Check if location permissions are granted
    if (areLocationPermissionsGranted(context)) {
        // Retrieve the last known location
        fusedLocationProviderClient.lastLocation
            .addOnSuccessListener { location ->
                location?.let {
                    // If location is not null, invoke the success callback with latitude and longitude
                    onGetLastLocationSuccess(Pair(it.latitude, it.longitude))
                }?.run {
                    onGetLastLocationIsNull()
                }
            }
            .addOnFailureListener { exception ->
                // If an error occurs, invoke the failure callback with the exception
                onGetLastLocationFailed(exception)
            }
    }
}


private fun areLocationPermissionsGranted(context: Context): Boolean {
    return (ActivityCompat.checkSelfPermission(
        context, Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
        context, Manifest.permission.ACCESS_COARSE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED)
} // End of areLocationPermissionsGranted()


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun WalkScreenContent(
    navController: NavController,
    walkViewModel: WalkViewModel,
) {
    val context = LocalContext.current

    /* chooseStartDialog */
    var showChooseStartDialog by rememberSaveable { mutableStateOf(true) }

    /* informDialog */
    var showInformDialog by remember { mutableStateOf(false) }

    /* BottomSheet*/
    val scaffoldState = rememberBottomSheetScaffoldState()
    val bottomSheetScope = rememberCoroutineScope()
    var bottomSheetStateOrdinal by remember { mutableIntStateOf(scaffoldState.bottomSheetState.currentValue.ordinal) }

    /* Icon Button */
    val rotationState by animateFloatAsState(
        targetValue = if (scaffoldState.bottomSheetState.currentValue.ordinal == 1) 180f else 0f
    )

    /* GoogleMap */
    // 카메라 포지션 상태 변경값을 감지해서 지도 변화
    val seoul = LatLng(37.5744, 126.9771)
    val cameraPositionState: CameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(seoul, 16f)
    }
    var uiSettings by remember { mutableStateOf(MapUiSettings()) }
    val properties by remember {
        mutableStateOf(MapProperties(mapType = MapType.NORMAL))
    }

    /* 최초 산책경로 가져오기 */
    val data =
        walkViewModel.result.collectAsState(initial = RequestState.Idle) // StateFlow의 상태변경 감지


    Surface(modifier = Modifier.fillMaxSize().background(White)) {
        BottomSheetScaffold(scaffoldState = scaffoldState,
            sheetShadowElevation = 20.dp,
            sheetPeekHeight = 60.dp,
            sheetContainerColor = Color.White,
            sheetDragHandle = {
                // 바텀 시트 핸들
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
//                    if (data.value.isLoading()) {
//                        Loader()
//                    } else if (data.value.isSuccess()) {
//                        Text(text = data.value.getSuccessData()!!.data.toString())
//                    } else if (data.value.isError()) {
//                        Text(text = data.value.getErrorMessage())
//                    }

                    IconButton(modifier = Modifier.rotate(rotationState).animateContentSize(
                        tween(durationMillis = 100, easing = FastOutLinearInEasing)
                    ), onClick = {

                        // walkViewModel.test()

                        bottomSheetStateOrdinal =
                            scaffoldState.bottomSheetState.currentValue.ordinal
                        bottomSheetScope.launch {
                            when (bottomSheetStateOrdinal) {
                                1 -> {
                                    scaffoldState.bottomSheetState.partialExpand()
                                }

                                else -> {
                                    scaffoldState.bottomSheetState.expand()
                                }
                            }
                        }
                    }) {
                        Icon(
                            painter = painterResource(R.drawable.ic_bottom_sheet_open),
                            contentDescription = null
                        )
                    }
                }
            },
            sheetContent = {
                /*
                    BottomSheet Content
                    바텀 시트 내부 콘텐트
                */

                val coroutineScope = rememberCoroutineScope()
                val snackbarHostState = remember { SnackbarHostState() }
                SnackbarHost(hostState = snackbarHostState)

                Column(
                    modifier = Modifier.fillMaxWidth().padding(top = defaultPadding),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Column(
                        Modifier.fillMaxWidth().padding(
                            start = defaultPadding, end = defaultPadding, bottom = 36.dp
                        ),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            "00:02:30",
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.height(30.dp))
                        WalkRecordingBox(context)
                        Spacer(modifier = Modifier.height(defaultPadding))
                        Button(
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.fillMaxWidth().height(44.dp),
                            onClick = {
                                // 산책 종료 버튼 클릭 이벤트
                                walkViewModel.setShowInfromDialog()
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = SpiroDiscoBall)
                        ) {
                            Text(
                                text = context.getString(R.string.walk_screen_walking_finish_button_content),
                                color = White,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = notoSansKoreanFontFamily
                            )
                        }
                    }
                }
            } // End of SheetContent
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                GoogleMap(
                    modifier = Modifier.fillMaxSize(),
                    cameraPositionState = cameraPositionState,
                    properties = properties,
                    uiSettings = uiSettings.copy(zoomControlsEnabled = false)
                )
            }
        } // End of BottomSheetScaffold {}
    } // End of Surface {}

    if (showChooseStartDialog) {/*
            산책로 리스트를 받아오는데, 이 리스트가 비어있으면
            다이얼로그에서 보여지는 텍스트들이 변경된다.

            산책로가 하나도 없을 경우,
            주변 산책로 없음, 홈으로 돌아가기로 보인다.
         */

        if (data.value.isLoading()) {
            Loader()
        } else if (data.value.isSuccess()) {
            WalkScreenChooseStartDialog(context.getString(R.string.walk_screen_dialog_choose_promenade_title),
                context.getString(R.string.walk_screen_dialog_choose_promenade_content),
                data.value.getSuccessData()!!,
                onClickDismiss = {
                    showChooseStartDialog = false
                    navController.popBackStack()
                },
                onClickConfirm = {
                    showChooseStartDialog = false
                })
        } else if (data.value.isError()) {
            Text(text = data.value.getErrorMessage())
        }
    }

    val showState = walkViewModel.showInfromDialog.collectAsState()
    when (showState.value) {
        true -> {
            WalkScreenInformDialogue(context.getString(R.string.walk_screen_inform_dialog_title),
                context.getString(R.string.walk_screen_inform_dialog_content),
                leftButtonText = context.getString(R.string.walk_screen_inform_dialog_close_button_content),
                rightButtonText = context.getString(R.string.walk_screen_inform_dialog_continue_button_content),
                onClickDismiss = {
                    walkViewModel.setShowInfromDialog()
                },
                onClickConfirm = {
                    walkViewModel.setShowInfromDialog()
                    navController.popBackStack()
                })
        }

        else -> null
    }

} // End of WalkScreenContent()


//@Composable
//@Preview
//fun WalkScreenContentPreview() {
//    MajuAppTheme() {
//        WalkScreenContent(rememberNavController(), WalkViewModel = hiltViewModel())
//    }
//} // End of WalkScreenContentPreview()