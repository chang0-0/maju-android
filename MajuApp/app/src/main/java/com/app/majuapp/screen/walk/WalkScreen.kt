package com.app.majuapp.screen.walk

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.app.majuapp.MainActivity
import com.app.majuapp.R
import com.app.majuapp.component.walk.AccessFineLocationPermissionTextProvider
import com.app.majuapp.component.walk.ActivityRecognitionPermissionTextProvider
import com.app.majuapp.component.walk.BearingSensorManager
import com.app.majuapp.component.walk.MapScreen
import com.app.majuapp.component.walk.PermissionDialog
import com.app.majuapp.component.walk.PostNotificationPermissionTextProvider
import com.app.majuapp.component.walk.StepCounterSensorManager
import com.app.majuapp.component.walk.WalkRecordingBox
import com.app.majuapp.component.walk.WalkScreenChooseStartDialog
import com.app.majuapp.component.walk.WalkScreenInformDialog
import com.app.majuapp.component.walk.WalkingRecordingTimer
import com.app.majuapp.component.walk.getCurrentLocation
import com.app.majuapp.component.walk.getLastUserLocation
import com.app.majuapp.service.RecordingService
import com.app.majuapp.ui.theme.SpiroDiscoBall
import com.app.majuapp.ui.theme.White
import com.app.majuapp.ui.theme.defaultPadding
import com.app.majuapp.ui.theme.notoSansKoreanFontFamily
import com.app.majuapp.util.findActivity
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.TimeZone

private const val TAG = "WalkScreen_창영"

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun WalkScreen(
    navController: NavController,
    walkViewModel: WalkViewModel = hiltViewModel(),
    walkingRecordViewModel: WalkingRecordViewModel = hiltViewModel()
) {/* Context */
    val context = LocalContext.current

    /* LifeCycle */
    val lifecycleOwner = LocalLifecycleOwner.current
    val stateFlow = lifecycleOwner.lifecycle.currentStateFlow
    val currentLifecycleState by stateFlow.collectAsState()

    /* Permission */
    WalkingTrailgetPermission(context) // 권한 설정

    /* Step Counter SensorManager */
    StepCounterSensorManager(walkingRecordViewModel) // SensorManager

    /* Bearing SensorManager */
    BearingSensorManager(context, walkingRecordViewModel)


    val lifeCycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifeCycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_CREATE) {

            } else if (event == Lifecycle.Event.ON_START) {

            } else if (event == Lifecycle.Event.ON_DESTROY) {
                // ON_STOP에서 Foreground Service 종료
                walkingService(context, RecordingService.Actions.STOP.toString())
            }
        }
        lifeCycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifeCycleOwner.lifecycle.removeObserver(observer)
            // walkingService(context, RecordingService.Actions.STOP.toString())
            Intent(
                context.applicationContext,
                RecordingService::class.java,
            ).also {
                it.action = RecordingService.Actions.STOP.toString()
                context.applicationContext.startService(it)
            }
        }
    }

    // 위치 정보 가져오기
    val coroutineScope = rememberCoroutineScope()
    getLastUserLocation(context, {}, {}, {})
    getCurrentLocation(context, {}, {}, walkViewModel = walkViewModel)


    /* State */
    val currentLocation by remember { walkViewModel.currentLocation }.collectAsStateWithLifecycle()
    // val walkingTrailData by remember { walkViewModel.walkingTrailData }.collectAsStateWithLifecycle()
    var showChooseStartDialog by remember { mutableStateOf(true) }

    LaunchedEffect(key1 = currentLocation != null, key2 = Unit) {
        // walkViewModel.getWalkingTrails()
        walkingRecordViewModel.setTodayStepCount(walkingRecordViewModel.moveStepCount.value)
    }

    if (currentLocation != null && showChooseStartDialog) {
        WalkScreenChooseStartDialog(context.getString(R.string.walk_screen_dialog_choose_promenade_title),
            context.getString(R.string.walk_screen_dialog_choose_promenade_content),
            onClickDismiss = {
                showChooseStartDialog = false
                navController.popBackStack()
            },
            onClickConfirm = {
                showChooseStartDialog = false
            })
    } else if (currentLocation != null && showChooseStartDialog == false) {
        WalkScreenContent(navController, walkViewModel)
    }
} // End of WalkScreen()


@SuppressLint("MissingPermission")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun WalkScreenContent(
    navController: NavController,
    walkViewModel: WalkViewModel,
    timerViewModel: TimerViewModel = hiltViewModel(),
    walkingRecordViewModel: WalkingRecordViewModel = hiltViewModel(),
) {/* Context */
    val context = LocalContext.current

    /* BottomSheet*/
    val scaffoldState = rememberBottomSheetScaffoldState()
    val bottomSheetScope = rememberCoroutineScope()
    var bottomSheetStateOrdinal by remember { mutableIntStateOf(scaffoldState.bottomSheetState.currentValue.ordinal) }

    /* Icon Button */
    val rotationState by animateFloatAsState(
        targetValue = if (scaffoldState.bottomSheetState.currentValue.ordinal == 1) 180f else 0f,
        label = ""
    )

    /* GoogleMap */
    val currentLocation by remember { walkingRecordViewModel.currentLocation }.collectAsStateWithLifecycle() // 사용자의 현재 위치 정보
    val currentChooseWalkingTrail by remember { walkViewModel.currentChooseWalkingTrail }.collectAsStateWithLifecycle() // 사용자의 선택한 산책로 정보

    /* EventBus */
    val lifeCycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifeCycleOwner) {

        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_CREATE) {
                walkingRecordViewModel.setCurrentLocationGenerate(walkViewModel.currentLocation.value!!)
            } else if (event == Lifecycle.Event.ON_START) {

            } else if (event == Lifecycle.Event.ON_STOP) {
                // walkingRecordViewModel.unregisterEventBus()
            } else if (event == Lifecycle.Event.ON_DESTROY) {
                // walkingRecordViewModel.unregisterEventBus()
            }
        }
        lifeCycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifeCycleOwner.lifecycle.removeObserver(observer)
        }
    }

    LaunchedEffect(Unit) {
        if (walkViewModel.currentChooseWalkingTrail.value != null) {
            val currentChoose = walkViewModel.currentChooseWalkingTrail.value!!
            walkViewModel.getWalkingTrailTrace(
                currentChoose.startLat,
                currentChoose.startLon,
                currentChoose.endLat,
                currentChoose.endLon,
            )
            Log.d(TAG, "currentChoose 내가 선택한 산책지: $currentChoose")
        }
    }


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
                    IconButton(modifier = Modifier.rotate(rotationState).animateContentSize(
                        tween(durationMillis = 100, easing = FastOutLinearInEasing)
                    ), onClick = {
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
                // BottomSheet Content
                val moveDist by walkingRecordViewModel.moveDist.collectAsStateWithLifecycle()
                val todayStepCount by remember { mutableIntStateOf(walkingRecordViewModel.todayStepCount.value) }
                val moveStepCount = 0

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
                        WalkingRecordingTimer()
                        Spacer(modifier = Modifier.height(30.dp))
                        WalkRecordingBox(
                            context, moveDist, moveStepCount, todayStepCount
                        ) // 현재 산책 기록 데이터
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
            // 전체 산책 뷰
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                if (currentChooseWalkingTrail != null && currentLocation != null) {
                    // 현재 위치, 선택한 산책지 데이터가 들어왔을 때,
                    MapScreen(
                        modifier = Modifier.padding(bottom = 40.dp),
                        startLocation = LatLng(
                            currentChooseWalkingTrail!!.startLat,
                            currentChooseWalkingTrail!!.startLon
                        ),
                        endLocation = LatLng(
                            currentChooseWalkingTrail!!.endLat, currentChooseWalkingTrail!!.endLon
                        ),
                        mapProperties = MapProperties(
                            isMyLocationEnabled = true, isBuildingEnabled = true
                        ),
                        mapUiSetting = MapUiSettings(compassEnabled = false),
                        eventBusEnable = true
                    )

                    LaunchedEffect(currentChooseWalkingTrail) {
                        walkingService(context, RecordingService.Actions.START.toString())
                        timerViewModel.startTimer() // 타이머 시작하기
                    }
                } else {
                    // 아직 데이터가 생성되지 않았을 때,
                    // 카메라 포지션 상태 변경값을 감지해서 지도 변화
                    val seoul = LatLng(37.5744, 126.9771)
                    val cameraPositionState: CameraPositionState = rememberCameraPositionState {
                        position = CameraPosition.fromLatLngZoom(seoul, 16f)
                    }
                    var uiSettings by remember { mutableStateOf(MapUiSettings()) }
                    val properties by remember {
                        mutableStateOf(MapProperties(mapType = MapType.NORMAL))
                    }

                    GoogleMap(
                        modifier = Modifier.fillMaxSize(),
                        cameraPositionState = cameraPositionState,
                        properties = properties,
                        uiSettings = uiSettings.copy(zoomControlsEnabled = false)
                    )
                }
            }

            ProvideCurrentLocation {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {

                }
            }


        } // End of BottomSheetScaffold {}

        /* BackHandle */
        BackHandler(enabled = true) {
            walkViewModel.setShowInfromDialog()
        }
    } // End of Surface {}


    val showState = walkViewModel.showInformDialog.collectAsStateWithLifecycle()
    when (showState.value) {
        true -> {
            WalkScreenInformDialog(context.getString(R.string.walk_screen_inform_dialog_title),
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

// val LocalTimeZone = compositionLocalOf { TimeZone.getDefault() }

@Composable
fun ProvideCurrentLocation(content: @Composable () -> Unit) {

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    // var currentLocation: LatLng by remember { mutableStateOf(getCurrentLocation(context, {}, {})) }
    var currentTimeZone: TimeZone by remember { mutableStateOf(TimeZone.getDefault()) }
    val LocalTimeZone = compositionLocalOf { TimeZone.getDefault() }


    DisposableEffect(Unit) {
        val receiver = object : BroadcastReceiver() {
            override fun onReceive(p0: Context?, p1: Intent?) {
                Log.d(TAG, "onReceive : ")
            }
        }

        coroutineScope.launch(Dispatchers.IO) {
            context.registerReceiver(receiver, IntentFilter(Intent.ACTION_TIMEZONE_CHANGED))
        }

        onDispose {
            context.unregisterReceiver(receiver)
        }
    }

    CompositionLocalProvider(
        value = LocalTimeZone provides currentTimeZone, content = content
    )
} // End of ProvideCurrentLocation()


@Composable
private fun WalkingTrailgetPermission(
    context: Context, walkViewModel: WalkViewModel = hiltViewModel()
) {
    val permissionsRequest = remember {
        mutableStateListOf<String>(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACTIVITY_RECOGNITION,
        )
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        permissionsRequest.add(Manifest.permission.POST_NOTIFICATIONS)
    }

    val permissionResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->
            permissionsRequest.forEach { permission ->
                walkViewModel.onPermissionResult(
                    permission = permission, isGranted = permissions[permission] == true
                )
            }
        })


    val dialogQue = walkViewModel.visiblePermissionDialogQueue
    val activity = context.findActivity() as MainActivity

    dialogQue.reversed().forEach { permission ->
        PermissionDialog(
            permissionTextProvider = when (permission) {
                Manifest.permission.ACCESS_FINE_LOCATION -> {
                    AccessFineLocationPermissionTextProvider()
                }

                Manifest.permission.ACTIVITY_RECOGNITION -> {
                    ActivityRecognitionPermissionTextProvider()
                }

                Manifest.permission.POST_NOTIFICATIONS -> {
                    PostNotificationPermissionTextProvider()
                }

                else -> return@forEach
            }, isPermanentlyDeclined = !shouldShowRequestPermissionRationale(
                activity, permission
            ), onDismiss = walkViewModel::dismissPermissionDialog, onOkClick = {
                walkViewModel.dismissPermissionDialog()
                permissionResultLauncher.launch(
                    arrayOf(permission)
                )
            }, onGoToAppSettingsClick = activity::openAppSettings
        )
    }

    LifecycleEventEffect(Lifecycle.Event.ON_START) {
        permissionResultLauncher.launch(permissionsRequest.toTypedArray())
    }
} // End of WalkingTrailgetPermission()


private fun walkingService(context: Context, option: String) {
    Intent(
        context.applicationContext,
        RecordingService::class.java,
    ).also {
        it.action = option
        context.applicationContext.startService(it)
    }
} // End of walkingService()

fun Activity.openAppSettings() {
    Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package", packageName, null)
    ).also(::startActivity)
} // End of Activity.openAppSettings()