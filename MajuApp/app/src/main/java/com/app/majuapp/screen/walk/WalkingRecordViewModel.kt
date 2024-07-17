package com.app.majuapp.screen.walk

import androidx.compose.runtime.*
import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class WalkingRecordViewModel @Inject constructor(
) : ViewModel() {

    // 현재 걸음수
    private val _stepCount = MutableStateFlow<Int>(0)
    val stepCount = _stepCount.asStateFlow()

    private val _todayStepCount = MutableStateFlow<Int>(0)
    val todayStepCount = _todayStepCount.asStateFlow()

    var stepCount2 by mutableIntStateOf(0)
        private set


    fun generateNewStepCount(newStepCount: Int) {
        stepCount2 = newStepCount
    } // End of generateNewStepCount()

    fun setTodayStepCount(newStepCount: Int) {
        _todayStepCount.value = newStepCount
    } // End of setStepCount()

    fun setStepCount(newStepCount: Int) {
        _stepCount.value = newStepCount
    } // End of setStepCount()


    // 현재 이동 거리
    private val _moveDist = MutableStateFlow<Double>(0.0)
    val moveDist = _moveDist.asStateFlow()
} // End of WalkingRecordViewModel()
