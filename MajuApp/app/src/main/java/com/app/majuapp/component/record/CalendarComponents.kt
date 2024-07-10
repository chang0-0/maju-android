package com.app.majuapp.component.record

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.app.majuapp.screen.record.CalendarUiState
import com.app.majuapp.ui.theme.QuickSilver
import java.time.YearMonth

@Composable
fun CalendarWidget(
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

@Composable
fun CalendarContentItem(
    date: CalendarUiState.Date,
    onClickListener: (CalendarUiState.Date) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.clickable {
        onClickListener(date)
    }) {
        Text(
            text = date.dayOfMonth,
            fontWeight = if (date.isSelected) {
                FontWeight.Bold
            } else {
                FontWeight.Normal
            },
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.align(Alignment.Center).padding(10.dp)
        )
    }
} // End of CalendarContentItem()

@Composable
fun CalendarContent(
    dates: List<CalendarUiState.Date>,
    onDateClickListener: (CalendarUiState.Date) -> Unit,
) {
    /*
        달력의 일수 표시
    */
    Column {
        var index = 0
        repeat(6) {
            if (index >= dates.size) return@repeat
            Row {
                repeat(7) {
                    val item = if (index < dates.size) dates[index] else CalendarUiState.Date.Empty
                    CalendarContentItem(
                        date = item,
                        onClickListener = onDateClickListener,
                        modifier = Modifier.weight(1f)
                    )
                    index++
                }
            }
        }
    }
} // End of CalendarContent()

@Composable
fun CalendarHeader(
    yearMonth: YearMonth,
    onPreviousMonthButtonClicked: (YearMonth) -> Unit,
    onNextMonthButtonClicked: (YearMonth) -> Unit,
) {
    /*
        달력의 헤더 부분
        좌우 아이콘, 년, 월
     */
    Row {
        IconButton(onClick = {
            onPreviousMonthButtonClicked.invoke(yearMonth.minusMonths(1))
        }) {
            Icon(
                imageVector = Icons.Filled.KeyboardArrowLeft, contentDescription = null
            )
        }
        Text(
            text = "${yearMonth.year}.${yearMonth.monthValue}",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f).align(Alignment.CenterVertically)
        )
        IconButton(modifier = Modifier, onClick = {
            onNextMonthButtonClicked.invoke(yearMonth.plusMonths(1))
        }) {
            Icon(
                imageVector = Icons.Filled.KeyboardArrowRight, contentDescription = null
            )
        }
    }
} // End of CalendarHeader()

@Composable
fun CalendarDayItem(day: String, modifier: Modifier = Modifier) {
    /*
        달력의 요일
     */
    Box(modifier = modifier) {
        Text(
            text = day,
            style = MaterialTheme.typography.bodyMedium,
            color = QuickSilver,
            modifier = Modifier.align(Alignment.Center).padding(8.dp)
        )
    }
} // End of CalendarDayItem()