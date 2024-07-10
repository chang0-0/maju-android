package com.app.majuapp.screen.record

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.app.majuapp.R
import com.app.majuapp.component.home.GrayBorderRoundedCard
import com.app.majuapp.component.record.CalendarWidget
import com.app.majuapp.component.record.RecordScreenCalendarColorInform
import com.app.majuapp.component.record.RecordScreenIconTextTitle
import com.app.majuapp.component.walk.WalkRecordingBox
import com.app.majuapp.domain.model.CultureLifeRecord
import com.app.majuapp.domain.model.RecordingDataModel
import com.app.majuapp.domain.model.RecordingWalkRecord
import com.app.majuapp.ui.theme.BrightGray
import com.app.majuapp.ui.theme.GoldenPoppy
import com.app.majuapp.ui.theme.MajuAppTheme
import com.app.majuapp.ui.theme.SonicSilver
import com.app.majuapp.ui.theme.SpiroDiscoBall
import com.app.majuapp.ui.theme.White
import com.app.majuapp.ui.theme.defaultPadding
import com.app.majuapp.ui.theme.notoSansKoreanFontFamily
import com.app.majuapp.ui.theme.roundedCornerPadding
import com.app.majuapp.util.DateUtil
import kotlinx.coroutines.launch

private const val TAG = "RecordScreen_창영"

@Composable
fun RecordScreen(navController: NavController) {
    RecordScreenContent(navController, recordingScreenDummyData)
} // End of RecordScreen()


@Composable
fun RecordScreenContent(navController: NavController, recordingData: RecordingDataModel) {
    // Context
    val context = LocalContext.current

    // modifier
    val modifier = Modifier

    // Snackbar State
    val rememberSnackbarState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Surface(
        modifier = modifier.fillMaxSize(), color = White,
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            item {
                Column(
                    modifier = modifier.padding(
                        start = 30.dp,
                        end = 30.dp,
                        top = 30.dp,
                    )
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().wrapContentHeight(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            context.getString(R.string.record_screen_recording_title),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = notoSansKoreanFontFamily
                        )
                        IconButton(modifier = Modifier, onClick = {}) {
                            Icon(
                                painter = painterResource(R.drawable.ic_default_user),
                                tint = SonicSilver,
                                contentDescription = null
                            )
                        }
                    }
                }
                RecordCalendar(rememberSnackbarState = rememberSnackbarState) // 달력
                Row(
                    modifier = Modifier.fillMaxWidth().wrapContentHeight()
                        .padding(start = defaultPadding + 4.dp, top = defaultPadding / 2),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    RecordScreenCalendarColorInform(
                        text = context.getString(R.string.record_screen_walk_recording_content),
                        color = SpiroDiscoBall
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    RecordScreenCalendarColorInform(
                        text = context.getString(R.string.record_screen_culture_life_content),
                        color = GoldenPoppy
                    )
                }
                Spacer(
                    modifier = Modifier.fillMaxWidth()
                        .padding(top = defaultPadding, bottom = defaultPadding)
                        .height(4.dp)
                        .background(BrightGray)
                )
                Column(
                    modifier = Modifier.fillMaxWidth()
                        .padding(start = 30.dp, end = 30.dp, top = defaultPadding)
                ) {
                    /*
                        산책 기록 LazyRow
                     */
                    RecordScreenIconTextTitle(
                        painterResource(R.drawable.ic_walk_record_check),
                        SpiroDiscoBall,
                        context.getString(R.string.record_screen_walk_recording_content)
                    )
                    LazyRow(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.spacedBy(28.dp)
                    ) {
                        items(recordingData.walkRecord.size) {
                            RecordScreenLazyItems(
                                modifier = Modifier.fillParentMaxWidth()
                            ) {

                            }
                        }
                    }

                    /*
                        문화 생활 LazyRow Item
                     */
                    RecordScreenIconTextTitle(
                        painterResource(R.drawable.ic_culture_life_check),
                        GoldenPoppy,
                        context.getString(R.string.record_screen_culture_life_content)
                    )
                }
            }
        } // End of LazyColumn()
        SnackbarHost(hostState = rememberSnackbarState)
    } // End of Surface()
} // End of RecordScreenContent()

@Composable
private fun RecordScreenLazyItems(
    modifier: Modifier, composableContent: @Composable () -> Unit
) {
    val context = LocalContext.current

    Column(
        modifier = modifier.fillMaxSize().wrapContentHeight()
    ) {
        GrayBorderRoundedCard(
            // 홈 화면 알림 카드
            modifier = Modifier.border(
                width = 2.dp, color = BrightGray, shape = RoundedCornerShape(
                    roundedCornerPadding
                )
            ),
            color = arrayListOf(Color.Transparent, Color.Transparent),
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(
                    start = 34.dp, end = 34.dp, top = 14.dp, bottom = 14.dp
                ),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "보라매공원 산책로",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    fontFamily = notoSansKoreanFontFamily
                )
                Spacer(modifier = Modifier.height(10.dp))
                WalkRecordingBox(context)
            }
        }
        composableContent() // 내부 컴포저블 함수
    }
} // End of RecordScreenLazyItems()


@Composable
private fun RecordCalendar(
    viewModel: RecordCalendarViewModel = viewModel(), rememberSnackbarState: SnackbarHostState
) {
    val calendarUiState by viewModel.calendarUiState.collectAsState() // 달력 날짜 전체 데이터
    val coroutineScope = rememberCoroutineScope()

    Surface(
        // modifier = Modifier.wrapContentHeight().verticalScroll(rememberScrollState()), color = White
        modifier = Modifier.wrapContentHeight(), color = White
    ) {
        CalendarWidget(days = DateUtil.daysOfWeek,
            yearMonth = calendarUiState.yearMonth,
            dates = calendarUiState.dates,
            onPreviousMonthButtonClicked = { prevMonth ->
                viewModel.toPreviousMonth(prevMonth)
            },
            onNextMonthButtonClicked = { nextMonth ->
                viewModel.toNextMonth(nextMonth)
            },
            onDateClickListener = {
                coroutineScope.launch {
                    rememberSnackbarState.showSnackbar("날짜 선택")
                }
            })
    }
} // End of RecordCalendar()


@Preview(showSystemUi = true)
@Composable
fun CalendarAppPreview() {
    MajuAppTheme() {
        RecordScreenContent(
            rememberNavController(), RecordingDataModel(
                arrayListOf(), arrayListOf()
            )
        )
    }
} // End of CalendarAppPreview()

@Preview(showBackground = true)
@Composable
fun LazyRowPreview() {
    MajuAppTheme() {
        RecordScreenLazyItems(
            modifier = Modifier
        ) {

        }
    }
}

private val recordingScreenDummyData = RecordingDataModel(
    arrayListOf(
        RecordingWalkRecord(
            "보라매공원 산책로",
            0.22,
            275,
        ), RecordingWalkRecord(
            "보라매공원 산책로",
            0.22,
            275,
        ), RecordingWalkRecord(
            "보라매공원 산책로",
            0.22,
            275,
        ), RecordingWalkRecord(
            "보라매공원 산책로",
            0.22,
            275,
        )
    ), arrayListOf(
        CultureLifeRecord("", "", "", "", "")
    )
)