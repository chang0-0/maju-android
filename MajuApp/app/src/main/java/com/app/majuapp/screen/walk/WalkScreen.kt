package com.app.majuapp.screen.walk

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
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
import com.app.majuapp.ui.theme.SonicSilver
import com.app.majuapp.ui.theme.SpiroDiscoBall
import com.app.majuapp.ui.theme.White
import com.app.majuapp.ui.theme.defaultPadding
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


    Surface(modifier = Modifier.fillMaxSize().background(White)) {
        BottomSheetScaffold(scaffoldState = scaffoldState,
            sheetPeekHeight = 120.dp,
            sheetContainerColor = Color.White,
            sheetDragHandle = {
                // 바텀 시트 핸들
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    IconButton(onClick = {
                        if (scaffoldState.bottomSheetState.currentValue.ordinal == 1) {
                            bottomSheetScope.launch {
                                scaffoldState.bottomSheetState.partialExpand()
                            }
                        } else {
                            bottomSheetScope.launch {
                                scaffoldState.bottomSheetState.expand()
                            }
                        }
                    }) {
                        Icon(
                            imageVector = if (scaffoldState.bottomSheetState.currentValue.ordinal == 1) {
                                Filled.ExpandMore
                            } else {
                                R.drawable.bottomsheetup_icon
                                Filled.ExpandLess
                            }, contentDescription = null
                        )
                    }
                }
            },
            sheetContent = {
                // 바텀 시트 내부 Contents
                Column(
                    modifier = Modifier.fillMaxWidth(),
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
                            Modifier.clip(RoundedCornerShape(8.dp)).fillMaxWidth().height(92.dp)
                                .background(color = BrightGray), contentAlignment = Alignment.Center
                        ) {
                            Row(
                                modifier = Modifier.matchParentSize(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                Box(
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
                                            text = "이동 거리",
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.Medium,
                                        )
                                        Row() {
                                            Text(
                                                textAlign = TextAlign.Center,
                                                text = "0.22",
                                                fontSize = 16.sp,
                                                fontWeight = FontWeight.Bold,
                                            )
                                            Text(
                                                textAlign = TextAlign.Center,
                                                text = "km",
                                                fontSize = 10.sp,
                                                fontWeight = FontWeight.Medium,
                                            )
                                        }
                                    }
                                }
                                Box(
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
                                            text = "걸음 수",
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
                                                text = "걸음",
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
                                text = "이동 종료",
                                color = White,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }


                    Spacer(modifier = Modifier.height(120.dp))
                }
            }) {
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
                        scaffoldState.bottomSheetState.expand()
                    }
                }) {
                    Text("바텀 시트 열기")
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