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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.app.majuapp.R
import com.app.majuapp.component.home.GrayBorderRoundedCard
import com.app.majuapp.component.record.CalendarContent
import com.app.majuapp.component.record.CalendarDayItem
import com.app.majuapp.component.record.CalendarHeader
import com.app.majuapp.component.record.RecordScreenCalendarColorInform
import com.app.majuapp.component.walk.WalkRecordingBox
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
import java.time.YearMonth

private const val TAG = "RecordScreen_창영"

@Composable
fun RecordScreen(navController: NavController) {
    RecordScreenContent(navController)
} // End of RecordScreen()


@Composable
fun RecordScreenContent(navController: NavController) {
    // Context
    val context = LocalContext.current

    // modifier
    val modifier = Modifier
    val brightGrayColor = ContextCompat.getColor(context, R.color.brightGray)

    val list = ArrayList<Int>()
    list.add(1)
    list.add(2)

    Surface(
        modifier = modifier.fillMaxSize(), color = White
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            item {
                Column(
                    modifier = modifier.padding(start = 30.dp, end = 30.dp, top = 30.dp)
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
                Spacer(
                    modifier = Modifier.fillMaxWidth().padding(10.dp)
                )
                RecordCalendar() // 달력
                Spacer(
                    modifier = Modifier.fillMaxWidth().padding(defaultPadding / 2)
                )
                Row(
                    modifier = Modifier.fillMaxWidth().wrapContentHeight()
                        .padding(start = defaultPadding),
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
                    modifier = Modifier.fillMaxWidth().padding(defaultPadding / 2)
                )
                Spacer(modifier = Modifier.fillMaxWidth().height(4.dp).background(BrightGray))
                Column(modifier = Modifier.padding(start = 30.dp, end = 30.dp, top = 30.dp)) {
                    Row(
                        modifier = Modifier.wrapContentHeight(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_walk_record_check),
                            tint = SpiroDiscoBall,
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = context.getString(R.string.record_screen_walk_recording_content),
                            fontFamily = notoSansKoreanFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            textAlign = TextAlign.Justify,
                            modifier = Modifier.alignByBaseline(),
                        )
                    }

                    GrayBorderRoundedCard(
                        // 홈 화면 알림 카드
                        modifier = Modifier.border(
                            width = 2.dp,
                            color = Color(brightGrayColor),
                            shape = RoundedCornerShape(
                                roundedCornerPadding
                            )
                        ),
                        color = arrayListOf(Color.Transparent, Color.Transparent),
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth()
                                .padding(
                                    start = 34.dp,
                                    end = 34.dp,
                                    top = 14.dp,
                                    bottom = 14.dp
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

                    Row(
                        modifier = Modifier.wrapContentHeight(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_culture_life_check),
                            tint = GoldenPoppy,
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = context.getString(R.string.record_screen_culture_life_content),
                            fontFamily = notoSansKoreanFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            textAlign = TextAlign.Justify,
                            modifier = Modifier.alignByBaseline(),
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    WalkRecordingBox(context)
                }
            }
        } // End of LazyColumn()
    }
} // End of RecordScreenContent()


@Composable
private fun RecordCalendar(viewModel: RecordCalendarViewModel = viewModel()) {
    val calendarUiState by viewModel.calendarUiState.collectAsState() // 달력 날짜 전체 데이터

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
                // TODO("set on date click listener")
            })
    }
} // End of RecordCalendar()

@Composable
private fun CalendarWidget(
    days: Array<String>,
    yearMonth: YearMonth,
    dates: List<CalendarUiState.Date>,
    onPreviousMonthButtonClicked: (YearMonth) -> Unit,
    onNextMonthButtonClicked: (YearMonth) -> Unit,
    onDateClickListener: (CalendarUiState.Date) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        CalendarHeader(
            yearMonth = yearMonth,
            onPreviousMonthButtonClicked = onPreviousMonthButtonClicked,
            onNextMonthButtonClicked = onNextMonthButtonClicked
        )
        Column(modifier = Modifier.padding(start = 10.dp, end = 10.dp)) {
            Row() {
                repeat(days.size) {
                    val item = days[it]
                    CalendarDayItem(item, modifier = Modifier.weight(1f))
                }
            }
            CalendarContent(
                dates = dates, onDateClickListener = onDateClickListener
            )
        }
    }
} // End of CalendarWidget()


@Preview(showSystemUi = true)
@Composable
fun CalendarAppPreview() {
    MajuAppTheme() {
        RecordScreenContent(rememberNavController())
    }
} // End of CalendarAppPreview()