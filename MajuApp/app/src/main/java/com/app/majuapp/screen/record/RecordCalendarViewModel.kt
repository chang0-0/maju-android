package com.app.majuapp.screen.record

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.YearMonth
import javax.inject.Inject


@HiltViewModel
class RecordCalendarViewModel @Inject constructor() : ViewModel() {
    private val dataSource by lazy { CalendarDataSource() }

    private val _calendarUiState = MutableStateFlow(CalendarUiState.Init)
    val calendarUiState: StateFlow<CalendarUiState> = _calendarUiState.asStateFlow()


    init {
        viewModelScope.launch {
            _calendarUiState.update { currentState ->
                currentState.copy(
                    dates = dataSource.getDates(currentState.yearMonth)
                )
            }
        }
    }

    fun toNextMonth(nextMonth: YearMonth) {
        viewModelScope.launch {
            _calendarUiState.update { currentState ->
                currentState.copy(
                    yearMonth = nextMonth, dates = dataSource.getDates(nextMonth)
                )
            }
        }
    }

    fun toPreviousMonth(prevMonth: YearMonth) {
        viewModelScope.launch {
            _calendarUiState.update { currentState ->
                currentState.copy(
                    yearMonth = prevMonth, dates = dataSource.getDates(prevMonth)
                )
            }
        }
    }
} // End of RecordCaledarViewModel class