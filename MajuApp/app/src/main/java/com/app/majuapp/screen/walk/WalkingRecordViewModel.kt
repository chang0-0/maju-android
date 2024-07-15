package com.app.majuapp.screen.walk

import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WalkingRecordViewModel @Inject constructor() : ViewModel() {

    // 현재 걸음수
    private val _stepCount = MutableStateFlow<Int>(0)
    val stepCount = _stepCount.asStateFlow()

    private val _todayStepCount = MutableStateFlow<Int>(0)
    val todayStepCount = _todayStepCount.asStateFlow()

    fun setTodayStepCount(newStepCount: Int) {
        _todayStepCount.value = newStepCount
    } // End of setStepCount()

    fun setStepCount(newStepCount: Int) {
        _stepCount.value = newStepCount
    } // End of setStepCount()


    // 현재 이동 거리
    private val _moveDist = MutableStateFlow<Double>(0.0)
    val moveDist = _moveDist.asStateFlow()


    private val _todayStepCount2 = mutableIntStateOf(0)
    val todayStepCount2 = _todayStepCount2

    fun fetchTodayStepCount() {
        viewModelScope.launch {
//            val stepCount = rememberStepCounterSensorState() // 이 함수는 suspend 함수로 가정
//            _todayStepCount2.intValue = stepCount.toInt()
        }
    }


} // End of WalkingRecordViewModel()