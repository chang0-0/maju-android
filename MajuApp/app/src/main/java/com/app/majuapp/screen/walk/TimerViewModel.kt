package com.app.majuapp.screen.walk

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TimerViewModel @Inject constructor() : ViewModel() {

    private val _timer = MutableStateFlow<Long>(0)
    val timer = _timer.asStateFlow()

    private var timerJob: Job? = null

    fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (true) {
                delay(1000)
                _timer.value++
            }
        }
    } // End of startTimer()

    fun pauseTimer() {
        timerJob?.cancel()
    } // End of pauseTimer()

    fun stopTimer() {
        _timer.value = 0
        timerJob?.cancel()
    } // End of stopTimer()

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
    } // End of onCleared()


} // End of TimerViewModel class