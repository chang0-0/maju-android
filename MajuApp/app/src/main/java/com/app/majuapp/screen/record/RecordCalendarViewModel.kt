package com.app.majuapp.screen.record

import android.icu.text.SimpleDateFormat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.majuapp.data.dto.NetworkDto
import com.app.majuapp.domain.model.CultureEventDomainModel
import com.app.majuapp.domain.usecase.RecordCalendarUsecase
import com.app.majuapp.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class RecordCalendarViewModel @Inject constructor(
    private val recordCalendarUsecase: RecordCalendarUsecase
) : ViewModel() {
    val TAG = "Calendar check"
    private val dataSource by lazy { CalendarDataSource() }

    private val _calendarUiState = MutableStateFlow(CalendarUiState.Init)
    val calendarUiState: StateFlow<CalendarUiState> = _calendarUiState.asStateFlow()

    val monthEvents = recordCalendarUsecase.monthEvents
    val walkingHistoryMonthEvents = recordCalendarUsecase.walkingHistoryMonthEvents
    val cultureLikeMonthEvents: StateFlow<Map<String, Boolean>> =
        recordCalendarUsecase.cultureLikeMonthEvents
    val cultureLikeDateEvents: StateFlow<List<CultureEventDomainModel>> =
        recordCalendarUsecase.cultureLikeDateEvents
    val cultureLikeMonthEventsNetworkResult: StateFlow<NetworkResult<NetworkDto<Map<String, Boolean>>>> =
        recordCalendarUsecase.cultureLikeMonthEventsNetworkResult
    val cultureLikeDateEventsNetworkResult: StateFlow<NetworkResult<NetworkDto<List<CultureEventDomainModel>>>> =
        recordCalendarUsecase.cultureLikeDateEventsNetworkResult

    val walkingHistoryMonthEventsNetworkResult: StateFlow<NetworkResult<NetworkDto<Map<String, Boolean>>>> = recordCalendarUsecase.walkingHistoryMonthEventsNetworkResult


    init {

        viewModelScope.launch {
            _calendarUiState.update { currentState ->
                currentState.copy(
                    dates = dataSource.getDates(currentState.yearMonth)
                )
            }
        }
        getCultureLikeMonthEvents(_calendarUiState.value.yearMonth.toString())
        getWalkingHistoryMonthEvents(_calendarUiState.value.yearMonth.toString())
        getCultureLikeDateEvents(SimpleDateFormat("yyyy-MM-dd").format(Date(System.currentTimeMillis())))
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

    fun toClickedDay(day: String) {
        viewModelScope.launch {
            _calendarUiState.update { currentState ->
                currentState.copy(
                    dates = dataSource.getDates(currentState.yearMonth).map { it ->
                        it.copy(
                            isSelected = it.dayOfMonth == day
                        )
                    }
                )
            }
        }
    }

    fun getCultureLikeMonthEvents(yearMonth: String) {
        viewModelScope.launch(Dispatchers.IO) {
            recordCalendarUsecase.getCultureLikeMonthEvents(yearMonth)
        }
    }

    fun getCultureLikeDateEvents(yearMonthDay: String) {
        viewModelScope.launch(Dispatchers.IO) {
            recordCalendarUsecase.getCultureLikeDateEvents(yearMonthDay)
        }
    }

    fun getWalkingHistoryMonthEvents(yearMonth: String) {
        viewModelScope.launch(Dispatchers.IO) {
            recordCalendarUsecase.getWalkingHistoryMonthEvents(yearMonth)
        }
    }

    fun getMonthEvents(yearMonth: String) {
        viewModelScope.launch(Dispatchers.IO) {
            recordCalendarUsecase.getMonthEvents(yearMonth)
        }
    }

} // End of RecordCaledarViewModel class