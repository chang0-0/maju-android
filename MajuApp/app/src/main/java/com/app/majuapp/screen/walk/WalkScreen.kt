package com.app.majuapp.screen.walk

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
import androidx.compose.foundation.layout.wrapContentWidth
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.app.majuapp.R
import com.app.majuapp.component.walk.WalkRecordingBox
import com.app.majuapp.component.walk.WalkScreenChooseStartDialog
import com.app.majuapp.ui.theme.MajuAppTheme
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
                /*
                    BottomSheet Content
                    바텀 시트 내부 콘텐트
                */

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

    if (showDialog) {
        /*
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


@Composable
@Preview
fun WalkScreenContentPreview() {
    MajuAppTheme() {
        WalkScreenContent(rememberNavController())
    }
} // End of WalkScreenContentPreview()