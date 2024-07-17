package com.app.majuapp.screen.walk

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.util.Log
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
import com.app.majuapp.component.Loader
import com.app.majuapp.component.walk.AccessFineLocationPermissionTextProvider
import com.app.majuapp.component.walk.ActivityRecognitionPermissionTextProvider
import com.app.majuapp.component.walk.MapScreen
import com.app.majuapp.component.walk.PermissionDialog
import com.app.majuapp.component.walk.PostNotificationPermissionTextProvider
import com.app.majuapp.component.walk.WalkRecordingBox
import com.app.majuapp.component.walk.WalkScreenChooseStartDialog
import com.app.majuapp.component.walk.WalkScreenInformDialogue
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val TAG = "WalkScreen_창영"

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun WalkScreen(
    navController: NavController,
    walkViewModel: WalkViewModel = hiltViewModel(),
    walkingRecordViewModel: WalkingRecordViewModel = hiltViewModel()
) {
    // context
    val context = LocalContext.current

    val lifecycleOwner = LocalLifecycleOwner.current
    val stateFlow = lifecycleOwner.lifecycle.currentStateFlow
    val currentLifecycleState by stateFlow.collectAsState()

    WalkingTrailgetPermission(context) // 권한 설정
    StepCounterSensorManager(walkingRecordViewModel)
    Log.d(
        TAG,
        "todayStepCount: ${walkingRecordViewModel.todayStepCount.value}"
    )

    val lifeCycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifeCycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_CREATE) {

            } else if (event == Lifecycle.Event.ON_START) {
                // Service 시작
            } else if (event == Lifecycle.Event.ON_DESTROY) {
                // ON_STOP에서 Foreground Service 종료
                Intent(
                    context.applicationContext,
                    RecordingService::class.java,
                ).also {
                    it.action = RecordingService.Actions.STOP.toString()
                    context.applicationContext.startService(it)
                }
            }
        }
        lifeCycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifeCycleOwner.lifecycle.removeObserver(observer)
            walkingService(context, RecordingService.Actions.STOP.toString())
        }
    }

    // 위치 정보를 가져오는데 성공

    var locationText by rememberSaveable { mutableStateOf("No location obtained :(") }
    var showPermissionResultText by rememberSaveable { mutableStateOf(false) }
    var permissionResultText by rememberSaveable { mutableStateOf("Permission Granted...") }

    getLastUserLocation(context, onGetLastLocationSuccess = {
        locationText = "Location using LAST-LOCATION: LATITUDE: ${it.lat}, LONGITUDE: ${it.lng}"
    }, onGetLastLocationFailed = { exception ->
        showPermissionResultText = true
        locationText = exception.localizedMessage ?: "Error Getting Last Location"
    }, onGetLastLocationIsNull = {
        // Attempt to get the current user location
        getCurrentLocation(context, onGetCurrentLocationSuccess = {
            locationText =
                "Location using CURRENT-LOCATION: LATITUDE: ${it.lat}, LONGITUDE: ${it.lng}"
            walkViewModel.setCurrentLocation(LatLng(it.lat!!, it.lng!!))
        }, onGetCurrentLocationFailed = {
            showPermissionResultText = true
            locationText = it.localizedMessage ?: "Error Getting Current Location"
        })
    })


    // 사용자의 현재 위치 정보가 저장된 값을 ViewModel에서 가져옵니다.
    val currentLocation by walkViewModel.currentLocation.collectAsStateWithLifecycle()
    // val todayStepCount by walkingRecordViewModel.todayStepCount.collectAsStateWithLifecycle()
    // Log.d(TAG, "WalkScreen -> todayStepCount:  $todayStepCount")

    if (currentLocation != null) {
        // 현재 위치 정보가 업데이트 되었을 때 현재 위치에 따른 산책로 추천

        val coroutine = rememberCoroutineScope()
        val walkingTrailData by walkViewModel.walkingTrailData.collectAsState()
        var showChooseStartDialog by rememberSaveable { mutableStateOf(true) }

        LaunchedEffect(Unit) {
            walkViewModel.getWalkingTrails()
        }

        val updateState = rememberUpdatedState("sdf")

        when (walkingTrailData) {
            is RequestState.Loading -> {
                Log.d(TAG, "WalkScreen -> RequestState.Loading")
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Loader()
                    Text(text = "현재 위치 정보를 가져오는 중입니다.")
                }
            }

            is RequestState.Success -> {
                Log.d(
                    TAG,
                    "WalkScreen -> walkingTrailData.getSuccessData()!!.data ${walkingTrailData.getSuccessData()!!.data}"
                )
                Log.d(TAG, "WalkScreen -> RequestState.Success")
                if (walkingTrailData.getSuccessData() != null && showChooseStartDialog) {
                    WalkScreenChooseStartDialog(context.getString(R.string.walk_screen_dialog_choose_promenade_title),
                        context.getString(R.string.walk_screen_dialog_choose_promenade_content),
                        walkingTrailData.getSuccessData()!!,
                        onClickDismiss = {
                            showChooseStartDialog = false
                            navController.popBackStack()
                        },
                        onClickConfirm = {
                            showChooseStartDialog = false
                        })
                } else {
                    WalkScreenContent(navController, walkViewModel)
                }
            }

            is RequestState.Error -> {
                Text(text = walkingTrailData.getErrorMessage())
            }

            else -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Loader()
                    Text(text = "현재 위치 정보를 가져오는 중입니다.")
                }
            }
        }
    } else {
        val coroutine = rememberCoroutineScope()
        val snackbarHostState = remember { SnackbarHostState() }
        SnackbarHost(hostState = snackbarHostState)

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val call: () -> Unit = {
                coroutine.launch(Dispatchers.IO) {
                    delay(5000)
                    snackbarHostState.showSnackbar("위치 정보를 가져오는데 실패하였습니다.")
                    navController.popBackStack()
                }
            }
            Loader()
            Text(text = "현재 위치 정보를 가져오는 중입니다.")
        }
    }
} // End of WalkScreen()


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun WalkScreenContent(
    navController: NavController,
    walkViewModel: WalkViewModel,
    timerViewModel: TimerViewModel = hiltViewModel(),
    walkingRecordViewModel: WalkingRecordViewModel = hiltViewModel(),
    // healthRecordViewModel: HealthRecordViewModel = hiltViewModel()
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
                // 바텀 시트 내부 콘텐트
                // 산책 기록 박스

                val moveDist by walkingRecordViewModel.moveDist.collectAsStateWithLifecycle()
                val todayStepCount by
                remember { mutableIntStateOf(walkingRecordViewModel.todayStepCount.value) }
//                val todayStepCount by walkingRecordViewModel.todayStepCount.collectAsStateWithLifecycle()
//                val moveStepCount by walkingRecordViewModel.stepCount.collectAsStateWithLifecycle()
                val moveStepCount = 0
                Log.d(TAG, "WalkRecordingBox todayStepCount: $todayStepCount")
                Log.d(TAG, "WalkRecordingBox moveStepCount: $moveStepCount")

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
            /* GoogleMap */
            val currentLocation by walkViewModel.currentLocation.collectAsStateWithLifecycle() // 사용자의 현재 위치 정보
            val currentChooseWalkingTrail by walkViewModel.currentChooseWalkingTrail.collectAsStateWithLifecycle() // 사용자의 선택한 산책로 정보

            // 전체 산책 뷰
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                if (currentChooseWalkingTrail != null && currentLocation != null) {
                    // 현재 위치, 선택한 산책지 데이터가 들어왔을 때,
                    MapScreen(
                        modifier = Modifier.padding(bottom = 40.dp), currentLocation = LatLng(
                            currentLocation!!.latitude, currentLocation!!.longitude

                        ), startLocation = LatLng(
                            currentChooseWalkingTrail!!.startLat,
                            currentChooseWalkingTrail!!.startLon
                        ), endLocation = LatLng(
                            currentChooseWalkingTrail!!.endLat, currentChooseWalkingTrail!!.endLon
                        )
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
        } // End of BottomSheetScaffold {}
    } // End of Surface {}


    val showState = walkViewModel.showInformDialog.collectAsStateWithLifecycle()
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

@Composable
fun StepCounterSensorManager(
    walkingRecordViewModel: WalkingRecordViewModel
) {
    val context = LocalContext.current
    val sensorManager = remember {
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    var stepCount by remember { mutableFloatStateOf(0f) }

    val stepCounterSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
    val sensorEventListener = remember {
        object : SensorEventListener {
            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
                // 정확도 변경 처리
            }

            override fun onSensorChanged(event: SensorEvent) {
                // 걸음 수 업데이트 처리
                stepCount = event.values[0]
                walkingRecordViewModel.setTodayStepCount(stepCount.toInt())
                Log.d(TAG, "onSensorChanged: ${walkingRecordViewModel.todayStepCount.value}")
            }
        }
    }

    DisposableEffect(Unit) {
        stepCounterSensor?.let {
            sensorManager.registerListener(
                sensorEventListener, it, SensorManager.SENSOR_DELAY_FASTEST
            )
        }

        onDispose {
            sensorManager.unregisterListener(sensorEventListener)
        }
    }

    // walkingRecordViewModel.setTodayStepCount(stepCount.toInt())
} // End of rememberStepCounterSensorState()

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