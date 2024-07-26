package com.app.majuapp.component.walk

import android.content.Context
import android.location.Location
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Divider
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.app.majuapp.R
import com.app.majuapp.component.Loader
import com.app.majuapp.component.fillMaxWidthSpacer
import com.app.majuapp.domain.model.walk.CurrentLocationReceiver
import com.app.majuapp.domain.model.walk.eventbus.EventBusEvent
import com.app.majuapp.screen.walk.RequestState
import com.app.majuapp.screen.walk.TimerViewModel
import com.app.majuapp.screen.walk.WalkViewModel
import com.app.majuapp.screen.walk.WalkingRecordViewModel
import com.app.majuapp.ui.theme.BrightGray
import com.app.majuapp.ui.theme.GoldenPoppy
import com.app.majuapp.ui.theme.OuterSpace
import com.app.majuapp.ui.theme.SilverSand
import com.app.majuapp.ui.theme.SonicSilver
import com.app.majuapp.ui.theme.TaupeGray
import com.app.majuapp.ui.theme.White
import com.app.majuapp.ui.theme.defaultPadding
import com.app.majuapp.ui.theme.dialogButtonRoundedCorner
import com.app.majuapp.ui.theme.dialogCornerPadding
import com.app.majuapp.ui.theme.dialogDefaultPadding
import com.app.majuapp.ui.theme.notoSansKoreanFontFamily
import com.app.majuapp.ui.theme.roundedCornerPadding
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.mutualmobile.composesensors.rememberHeadingSensorState
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe


private const val TAG = "WalkComponents_창영"


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WalkScreenChooseStartDialog(
    title: String,
    content: String,
    onClickConfirm: () -> Unit,
    onClickDismiss: () -> Unit,
    walkViewModel: WalkViewModel = hiltViewModel()
) {
    // 산책 난이도 및 출발지 선택 다이얼로그
    // Context
    val context = LocalContext.current

    var showAnimatedDialog by remember { mutableStateOf(false) }
    var graphicVisible = remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { graphicVisible.value = true }

    var animateIn = remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        animateIn.value = true
        graphicVisible.value = true
        walkViewModel.getWalkingTrails()
    }

    /* State */
    val walkingTrailData by remember { walkViewModel.walkingTrailData }.collectAsStateWithLifecycle()

    /* GoogleMap */
    val walkingPagerState = rememberPagerState(pageCount = {
        if (walkingTrailData.isSuccess() && walkingTrailData.getSuccessData() != null) {
            walkingTrailData.getSuccessData()!!.data.size
        } else {
            0
        }
    })


    AnimatedVisibility(
        visible = graphicVisible.value, enter = expandVertically(
            animationSpec = spring(stiffness = Spring.StiffnessMediumLow),
            expandFrom = Alignment.CenterVertically,
        )
    ) {
        Dialog(
            onDismissRequest = { onClickDismiss() },
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = false,
            ),
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.shadow(8.dp, shape = RoundedCornerShape(16.dp))
                    .background(Color.Black.copy(alpha = .9f))
                    .clip(RoundedCornerShape(dialogCornerPadding)).background(White).padding(
                        start = dialogDefaultPadding,
                        end = dialogDefaultPadding,
                        top = dialogDefaultPadding,
                        bottom = 16.dp
                    ),
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth().wrapContentHeight(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text(
                        modifier = Modifier,
                        text = title,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = content,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.ExtraLight,
                        color = SonicSilver,
                        modifier = Modifier.align(
                            Alignment.CenterHorizontally
                        ),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(18.dp))
                    Box(
                        modifier = Modifier.clip(RoundedCornerShape(roundedCornerPadding))
                            .fillMaxWidth().zIndex(1f).height(260.dp).border(
                                2.dp, BrightGray, shape = RoundedCornerShape(roundedCornerPadding)
                            ).padding(top = defaultPadding, bottom = defaultPadding),
                        contentAlignment = Alignment.BottomCenter
                    ) {
                        when (walkingTrailData) {
                            is RequestState.Loading -> {
                                Column(
                                    modifier = Modifier.fillMaxSize(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Loader()
                                }
                            }

                            is RequestState.Success -> {
                                if (walkingTrailData.getSuccessData() != null) {
                                    val walkingTrailDataList by remember {
                                        mutableStateOf(
                                            walkingTrailData.getSuccessData()!!.data
                                        )
                                    }

                                    Box(modifier = Modifier.zIndex(1f)) {
                                        if (walkingTrailDataList.isEmpty()) {
                                            Text(
                                                "주변 산책로가\n존재하지 않습니다.",
                                                fontSize = 14.sp,
                                                fontWeight = FontWeight.Bold,
                                                fontFamily = notoSansKoreanFontFamily
                                            )
                                        } else {
                                            Box(modifier = Modifier.zIndex(1f)) {
                                                HorizontalPager(
                                                    modifier = Modifier, state = walkingPagerState
                                                ) { page ->
                                                    val coordinates = walkingTrailDataList[page]
                                                    Box(
                                                        modifier = Modifier.zIndex(1f)
                                                    ) {
                                                        Box(
                                                            modifier = Modifier.padding(
                                                                top = 22.dp, start = 10.dp
                                                            ).zIndex(3f)
                                                        ) {
                                                            SubcomposeAsyncImage(
                                                                modifier = Modifier.size(60.dp),
                                                                model = ImageRequest.Builder(
                                                                    LocalContext.current
                                                                ).data(
                                                                    when (walkingTrailDataList[page].level) {
                                                                        "상" -> R.drawable.ic_difficulty_level
                                                                        "중" -> R.drawable.ic_intermediate_difficulty_level
                                                                        else -> R.drawable.ic_easy_difficulty_leve
                                                                    }
                                                                ).crossfade(true).build(),
                                                                contentScale = ContentScale.Crop,
                                                                alignment = Alignment.TopStart,
                                                                contentDescription = "산책로 난이도"
                                                            )
                                                        }
                                                        Box(modifier = Modifier.zIndex(2f)) {
                                                            Column(
                                                                modifier = Modifier.fillMaxSize()
                                                                    .zIndex(1f),
                                                                verticalArrangement = Arrangement.Center,
                                                                horizontalAlignment = Alignment.CenterHorizontally
                                                            ) {
                                                                Text(
                                                                    text = walkingTrailDataList[page].name,
                                                                    fontSize = 16.sp,
                                                                    fontWeight = FontWeight.Bold,
                                                                    color = Color.Black,
                                                                    textAlign = TextAlign.Center
                                                                )
                                                                Spacer(
                                                                    modifier = Modifier.fillMaxWidth()
                                                                        .height(18.dp)
                                                                )
                                                                Box(
                                                                    modifier = Modifier.padding(
                                                                        start = defaultPadding,
                                                                        end = defaultPadding
                                                                    ).fillMaxSize().height(200.dp)
                                                                        .clip(
                                                                            RoundedCornerShape(
                                                                                roundedCornerPadding
                                                                            )
                                                                        ).background(
                                                                            OuterSpace,
                                                                            shape = RoundedCornerShape(
                                                                                roundedCornerPadding
                                                                            )
                                                                        ),
                                                                    contentAlignment = Alignment.TopStart
                                                                ) {
                                                                    MapScreen(
                                                                        modifier = Modifier.fillMaxSize(),
                                                                        LatLng(
                                                                            coordinates.startLat,
                                                                            coordinates.startLon
                                                                        ),
                                                                        LatLng(
                                                                            coordinates.startLat,
                                                                            coordinates.startLon
                                                                        ),
                                                                        LatLng(
                                                                            coordinates.endLat,
                                                                            coordinates.endLon
                                                                        ),
                                                                        mapProperties = MapProperties(
                                                                            isMyLocationEnabled = false,
                                                                            isBuildingEnabled = false
                                                                        ),
                                                                        mapUiSetting = MapUiSettings(
                                                                            compassEnabled = false
                                                                        ),
                                                                    )
                                                                }
                                                            }
                                                        }
                                                    }


                                                }
                                            }

                                        }
                                    }
                                }


                            }

                            is RequestState.Error -> {

                            }

                            else -> {

                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(18.dp))
                    Row(
                        modifier = Modifier.padding(start = defaultPadding, end = defaultPadding),
                        Arrangement.spacedBy(8.dp)
                    ) {

                        /* GoogleMap */
                        WalkComponentButton(
                            buttonText = context.getString(R.string.walk_screen_dialog_start_dialog_start_button_content),
                            GoldenPoppy,
                            onClickConfirm = {
                                onClickConfirm()
                            },
                            Modifier.weight(1f),
                            currentPage = walkingPagerState.currentPage
                        )
                    }
                }
            }
        }

        DisposableEffect(Unit) {
            onDispose {
                showAnimatedDialog = false
            }
        }
    }
} // End of WalkScreenChooseStartDialogue()

private val currentLocationReceiver = CurrentLocationReceiver()

@Composable
fun MapScreen(
    modifier: Modifier,
    currentLocation: LatLng = LatLng(0.0, 0.0),
    startLocation: LatLng = LatLng(0.0, 0.0),
    endLocation: LatLng = LatLng(0.0, 0.0),
    walkingRecordViewModel: WalkingRecordViewModel = hiltViewModel(),
    walkViewModel: WalkViewModel = hiltViewModel(),
    mapProperties: MapProperties,
    mapUiSetting: MapUiSettings,
    eventBusEnable: Boolean = false,
) {
    val currentLocation by remember { walkingRecordViewModel.currentLocation }.collectAsStateWithLifecycle()
    val trail by remember { walkViewModel.walkingTrailTrace }.collectAsStateWithLifecycle()

    Log.d(TAG, "MapScreen -> getTrail : $trail")
    Log.d(TAG, "MapScreen -> currentLocation :  $currentLocation")

    val coroutinScope = rememberCoroutineScope()
    val heading = rememberHeadingSensorState(autoStart = true)
    val azimuth by remember { walkingRecordViewModel.azimuth }.collectAsState()
    var cameraPositionState: CameraPositionState = rememberCameraPositionState {}

    if (currentLocation != null) {
        cameraPositionState = rememberCameraPositionState {
            isMoving
            position = CameraPosition.builder()
                .target(LatLng(currentLocation!!.latitude, currentLocation!!.longitude)).zoom(16f)
                .bearing(azimuth.toFloat()).build()
        }
    } else {
        cameraPositionState = rememberCameraPositionState {
            isMoving
            position = CameraPosition.builder().target(startLocation).zoom(16f).build()
        }
    }

    /* EventBus */
    DisposableEffect(key1 = Unit, key2 = trail.isSuccess(), key3 = eventBusEnable == true) {
        val reg = object {
            @Subscribe
            fun onCurrentLocationEvent(event: EventBusEvent.CurrentLocationEvent) {
                Log.d(TAG, "onCurrentLocationEvent: $event")
            }
        }

        EventBus.getDefault().register(reg)

        onDispose {
            EventBus.getDefault().unregister(reg)
        }
    }

    var location by remember { mutableStateOf<Location?>(null) }
    GoogleMap(
        modifier = modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        properties = mapProperties,
        uiSettings = mapUiSetting,
    ) {
        if (currentLocation != null) {
            MarkerOptions().position(currentLocation!!)
//            Marker(
//                state = MarkerState(
//                    position = LatLng(currentLocation!!.latitude, currentLocation!!.longitude)
//                )
//            )

//            coroutinScope.launch(Dispatchers.Main) {
//                cameraPositionState.animate(
//                    CameraUpdateFactory.newLatLng(currentLocation!!),
//                    1
//                )
//            }
        }


        Marker(
            state = MarkerState(
                position = endLocation
            )
        )
        Marker(
            state = MarkerState(
                position = startLocation
            )
        )
    }
} // End of MapScreen()


@Composable
fun WalkScreenInformDialog(
    title: String,
    content: String,
    leftButtonText: String,
    rightButtonText: String,
    onClickDismiss: () -> Unit,
    onClickConfirm: () -> Unit,
) {
    var showAnimatedDialog by remember { mutableStateOf(false) }
    var graphicVisible = remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { graphicVisible.value = true }

    var animateIn = remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { animateIn.value = true }

    AnimatedVisibility(
        visible = graphicVisible.value, enter = expandVertically(
            animationSpec = spring(stiffness = Spring.StiffnessMediumLow),
            expandFrom = Alignment.CenterVertically,
        )
    ) {
        Dialog(
            onDismissRequest = { onClickDismiss() },
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = false,
            ),
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.shadow(8.dp, shape = RoundedCornerShape(16.dp))
                    .background(Color.Black.copy(alpha = .9f))
                    .clip(RoundedCornerShape(dialogCornerPadding)).background(White).padding(
                        start = dialogDefaultPadding,
                        end = dialogDefaultPadding,
                        top = dialogDefaultPadding,
                        bottom = 16.dp
                    ),
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth().wrapContentHeight(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text(
                        modifier = Modifier,
                        text = title,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                    fillMaxWidthSpacer(Modifier, defaultPadding)
                    Text(
                        text = content,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.ExtraLight,
                        color = SonicSilver,
                        modifier = Modifier.align(
                            Alignment.CenterHorizontally
                        ),
                        textAlign = TextAlign.Center
                    )
                    fillMaxWidthSpacer(Modifier, defaultPadding)
                    Row(
                        modifier = Modifier.padding(
                            start = defaultPadding, end = defaultPadding
                        ), Arrangement.spacedBy(8.dp)
                    ) {
                        WalkComponentButton(
                            buttonText = leftButtonText,
                            TaupeGray,
                            onClickConfirm,
                            Modifier.weight(1f),
                            -1
                        )
                        WalkComponentButton(
                            buttonText = rightButtonText,
                            GoldenPoppy,
                            onClickDismiss,
                            Modifier.weight(1f),
                            -1
                        )
                    }
                }
            }
        }

        DisposableEffect(Unit) {
            onDispose {
                showAnimatedDialog = false
            }
        }
    }

} // End of WalkScreenInformDialogue()


@Composable
fun WalkRecordingBox(
    context: Context,
    moveDist: Double,
    moveStepCount: Int,
    todayStepCount: Int = 0,
    walkingRecordViewModel: WalkingRecordViewModel = hiltViewModel()
) {

    /*
        바텀 시트 내부
        이동 거리, 걸음 수가 보이는 회색 박스
     */
    val todayStepCount = walkingRecordViewModel.todayStepCount.value
    val moveStepCount = walkingRecordViewModel.moveStepCount.collectAsStateWithLifecycle()

    Box(
        Modifier.clip(RoundedCornerShape(8.dp)).fillMaxWidth().height(92.dp)
            .background(color = BrightGray), contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.matchParentSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                // 이동 거리 박스
                modifier = Modifier.fillMaxWidth().wrapContentHeight().weight(1f).align(
                    Alignment.CenterVertically,
                ),
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth().wrapContentHeight(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        textAlign = TextAlign.Center,
                        color = SonicSilver,
                        text = context.getString(R.string.walk_screen_walking_bottom_sheet_box_distanced_traveled),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                    )
                    Row {
                        Text(
                            textAlign = TextAlign.Center,
                            text = "$moveDist",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                        )
                        Text(
                            textAlign = TextAlign.Center,
                            text = context.getString(R.string.walk_screen_walking_bottom_sheet_box_distanced_traveled_unit),
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Medium,
                        )
                    }
                }
            }
            Spacer(
                // 회색 박스 중간 구분선
                modifier = Modifier.width(1.dp).fillMaxHeight().padding(
                    top = defaultPadding + 8.dp, bottom = defaultPadding + 8.dp
                ).background(SilverSand)
            )
            Box(
                // 걸음 수 박스
                modifier = Modifier.fillMaxWidth().wrapContentHeight().weight(1f).align(
                    Alignment.CenterVertically,
                ),
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth().wrapContentHeight(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        color = SonicSilver,
                        textAlign = TextAlign.Center,
                        text = context.getString(R.string.walk_screen_walking_bottom_sheet_box_step_count_title),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                    )
                    Row {
                        Text(
                            textAlign = TextAlign.Center,
                            text = "${moveStepCount.value - todayStepCount}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                        )
                        Text(
                            textAlign = TextAlign.Center,
                            text = context.getString(R.string.walk_screen_walking_bottom_sheet_box_step_count_unit),
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Medium,
                        )
                    }
                }
            }
        }
    }
} // End of WalkRecordingBox

@Composable
fun WalkComponentButton(
    buttonText: String,
    buttonColor: Color,
    onClickConfirm: () -> Unit,
    modifier: Modifier,
    currentPage: Int,
    walkViewModel: WalkViewModel = hiltViewModel()
) {
    val walkingTrailData by remember { walkViewModel.walkingTrailData }.collectAsStateWithLifecycle()
    Button(shape = RoundedCornerShape(dialogButtonRoundedCorner),
        modifier = modifier.fillMaxWidth().wrapContentHeight(),
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonColor,
        ),
        onClick = {
            onClickConfirm()

            if (currentPage != -1 && walkingTrailData.isSuccess() && walkingTrailData.getSuccessData() != null) {
                walkViewModel.setCurrentChooseWalkingTrail(walkingTrailData.getSuccessData()!!.data[currentPage])
            }
        }) {
        Text(
            buttonText,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = notoSansKoreanFontFamily
        )
    }
} // End of WalkComponentButton()

@Composable
fun WalkingRecordingTimer(
    timerViewModel: TimerViewModel = hiltViewModel()
) {
    val timerValue by timerViewModel.timer.collectAsState()

    Text(
        text = timerValue.formatTime(),
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        color = Color.Black
    )
} // End of WalkingRecordingTimer()

@Composable
fun PermissionDialog(
    permissionTextProvider: PermissionTextProvider,
    isPermanentlyDeclined: Boolean,
    onDismiss: () -> Unit,
    onOkClick: () -> Unit,
    onGoToAppSettingsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(onDismissRequest = onDismiss, buttons = {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Divider()
            Text(
                text = if (isPermanentlyDeclined) {
                    "설정으로 가기"
                } else {
                    "확인"
                },
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth().clickable {
                    if (isPermanentlyDeclined) {
                        onGoToAppSettingsClick()
                    } else {
                        onOkClick()
                    }
                }.padding(16.dp)
            )
        }
    }, title = {
        Text(text = "권한 요청 알림")
    }, text = {
        Text(
            text = permissionTextProvider.getDescription(
                isPermanentlyDeclined = isPermanentlyDeclined
            )
        )
    }, modifier = modifier
    )
} // End of PermissionDialog()

interface PermissionTextProvider {
    fun getDescription(isPermanentlyDeclined: Boolean): String
}

class AccessFineLocationPermissionTextProvider : PermissionTextProvider {
    override fun getDescription(isPermanentlyDeclined: Boolean): String {
        return if (isPermanentlyDeclined) {
            "위치 권한을 거부하셨습니다.\n앱 설정으로 이동하여 권한을 허용해주세요"
        } else {
            "해당 기능은 산책 기록을 위하여 권한을 필요로 합니다."
        }
    }
}

class ActivityRecognitionPermissionTextProvider : PermissionTextProvider {
    override fun getDescription(isPermanentlyDeclined: Boolean): String {
        return if (isPermanentlyDeclined) {
            "신체 활동 권한을 거부하셨습니다.\n앱 설정으로 이동하여 권한을 허용해주세요"
        } else {
            "해당 기능은 산책 중 걸음 수 측정을 위해 권한을 필요로 합니다."
        }
    }
}

class PostNotificationPermissionTextProvider : PermissionTextProvider {
    override fun getDescription(isPermanentlyDeclined: Boolean): String {
        return if (isPermanentlyDeclined) {
            "알림 권한을 거부하셨습니다.\n앱 설정으로 이동하여 권한을 허용해주세요"
        } else {
            "해당 기능은 백그라운드 기록을 위해 권한을 필요로 합니다."
        }
    }
}


fun Long.formatTime(): String {
    val hours = this / 3600
    val minutes = (this % 3600) / 60
    val remainingSeconds = this % 60
    return String.format("%02d:%02d:%02d", hours, minutes, remainingSeconds)
} // End of Long.formatTime()
