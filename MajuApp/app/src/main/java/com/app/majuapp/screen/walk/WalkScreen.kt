package com.app.majuapp.screen.walk

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.app.majuapp.R
import com.app.majuapp.component.WalkScreenChooseStartDialog
import com.app.majuapp.ui.theme.BrightGray
import com.app.majuapp.ui.theme.SilverSand
import com.app.majuapp.ui.theme.SonicSilver
import com.app.majuapp.ui.theme.SpiroDiscoBall
import com.app.majuapp.ui.theme.White
import com.app.majuapp.ui.theme.defaultPadding
import com.app.majuapp.ui.theme.notoSansKoreanFontFamily
import kotlinx.coroutines.launch

private const val TAG = "WalkScreen_창영"

@Composable
fun WalkScreen(navController: NavController) {
    WalkScreenContent(navController)
} // End of WalkScreen()

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun WalkScreenContent(navController: NavController) {
    val context = LocalContext.current

    /* Dialog */
    var showDialog by rememberSaveable { mutableStateOf(true) }


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
                    IconButton(
                        modifier = Modifier.rotate(rotationState)
                            .animateContentSize(
                                tween(durationMillis = 100, easing = FastOutLinearInEasing)
                            ),
                        onClick = {
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
                        Box(
                            // 바텀 시트 이동 거리, 걸음 수가 보이는 회색 박스
                            Modifier.clip(RoundedCornerShape(8.dp)).fillMaxWidth().height(92.dp)
                                .background(color = BrightGray),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(
                                modifier = Modifier.matchParentSize(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                Box(
                                    // 이동 거리 박스
                                    modifier = Modifier.fillMaxWidth().wrapContentHeight()
                                        .weight(1f).align(
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
                                                text = "0.22",
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
                                    modifier = Modifier.width(1.dp).fillMaxHeight()
                                        .padding(
                                            top = defaultPadding + 8.dp,
                                            bottom = defaultPadding + 8.dp
                                        )
                                        .background(SilverSand)
                                )
                                Box(
                                    // 걸음 수 박스
                                    modifier = Modifier.fillMaxWidth().wrapContentHeight()
                                        .weight(1f).align(
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
                                        Row() {
                                            Text(
                                                textAlign = TextAlign.Center,
                                                text = "275",
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
                        Spacer(modifier = Modifier.height(defaultPadding))
                        Button(
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.fillMaxWidth().height(44.dp),
                            onClick = {},
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
                modifier = Modifier.fillMaxSize().background(White).padding(defaultPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Button(modifier = Modifier.height(40.dp).wrapContentWidth(), onClick = {
                    showDialog = true
                }) {
                    Text("다이얼로그 활성화")
                }
                Button(modifier = Modifier.height(40.dp).wrapContentWidth(), onClick = {
                    bottomSheetScope.launch {
                        bottomSheetStateOrdinal =
                            scaffoldState.bottomSheetState.currentValue.ordinal
                        if (bottomSheetStateOrdinal == 1) {
                            scaffoldState.bottomSheetState.partialExpand()
                        } else {
                            scaffoldState.bottomSheetState.expand()
                        }
                    }
                }) {
                    Text(
                        "바텀 시트 ${
                            if (bottomSheetStateOrdinal == 1) "닫기" else {
                                "열기"
                            }
                        }  "
                    )
                }
            }
        } // End of BottomSheetScaffold {}
    } // End of Surface {}

    if (showDialog) {/*
            산책로 리스트를 받아오는데, 이 리스트가 비어있으면
            다이얼로그에서 보여지는 텍스트들이 변경된다.

            산책로가 하나도 없을 경우,
            주변 산책로 없음, 홈으로 돌아가기로 보인다.
         */

        WalkScreenChooseStartDialog(context.getString(R.string.walk_screen_dialog_choose_promenade_title),
            context.getString(R.string.walk_screen_dialog_choose_promenade_content),
            onClickDismiss = {
                showDialog = false
                navController.popBackStack()
            },
            onClickConfirm = {
                showDialog = false
            })
    }
} // End of WalkScreenContent()

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun WalkScreenCustomBottomSheet() {
    ModalBottomSheet(
        modifier = Modifier,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false),
        onDismissRequest = {},
        shape = RoundedCornerShape(
            topStart = 10.dp, topEnd = 10.dp
        )
    ) {
        WalkScreenCustomBottomSheetContainer()
    }
} // End of WalkScreenCustomBottomSheet()

@Composable
private fun WalkScreenCustomBottomSheetContainer() {
    Scaffold(topBar = {
        Column {
            Text(
                text = "Theme",
                modifier = Modifier.height(75.dp).padding(start = 29.dp, top = 26.dp),
                fontSize = 23.sp
            )
            Divider(color = Color.Black, thickness = 1.dp)
        }
    }) {
        Column(modifier = Modifier.padding(it)) {
            Text(
                text = "Select theme",
                modifier = Modifier.padding(start = 29.dp, top = 20.dp, bottom = 10.dp)
                    .height(40.dp),
                fontSize = 20.sp
            )
            CustomItem("Light")
            CustomItem("Dark")
            CustomItem("System default")
        }
    }

} // End of WalkScreenCustomBottomSheetContainer()

@Composable
fun CustomItem(text: String) {
    Row(modifier = Modifier.height(40.dp), verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = painterResource(id = R.drawable.tokyo_test_image),
            modifier = Modifier.padding(start = 31.dp, top = 9.dp),
            contentDescription = ""
        )
        Text(
            text = text,
            modifier = Modifier.height(40.dp).padding(start = 20.dp, top = 11.dp),
            fontSize = 18.sp
        )
    }
}