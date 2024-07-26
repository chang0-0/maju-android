package com.app.majuapp.screen.walk

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

private const val TAG = "WalkingRecordViewModel_창영"

@HiltViewModel
class WalkingRecordViewModel @Inject constructor(
) : ViewModel() {

    init {
        Log.d(TAG, "언제 실행됩니까?: ")
        Log.e(TAG, "already registered event bus for " + this);
    }

    // 현재 걸음수
    private val _stepCount = MutableStateFlow<Int>(0)
    val moveStepCount = _stepCount.asStateFlow()

    // 누적 걸음수
    private val _todayStepCount = MutableStateFlow<Int>(0)
    val todayStepCount = _todayStepCount.asStateFlow()

    fun setTodayStepCount(newStepCount: Int) {
        _todayStepCount.value = newStepCount
    } // End of setStepCount()

    fun setStepCount(newStepCount: Int) {
        _stepCount.value = newStepCount
    } // End of setStepCount()

    private val _azimuth = MutableStateFlow<Int>(0)
    val azimuth = _azimuth.asStateFlow()

    // 카메라 방향
    fun setAzimuth(newAzimuth: Int) {
        _azimuth.value = newAzimuth
    } // End of setAzimuth()

    // 현재 이동 거리
    private val _moveDist = MutableStateFlow<Double>(0.0)
    val moveDist = _moveDist.asStateFlow()


    // 현재 유저 위치
    private val _currentLocation = MutableStateFlow<LatLng?>(null)
    val currentLocation = _currentLocation.asStateFlow()

    fun setCurrentLocationGenerate(generateLoccation: LatLng) {
        _currentLocation.value = generateLoccation
    } // End of setGenerate()

    fun setCurrentLocation(newLocation: LatLng) {
        _currentLocation.value = newLocation
    } // End of setCurrentLocation()


    /* EventBus */
    private var isEventBusRegistered by mutableStateOf(false)

//    fun registerEventBus() {
//        Log.d(TAG, "isEventBusRegistered: $isEventBusRegistered ")
//
//        if (!isEventBusRegistered) {
//            EventBus.getDefault().register(this)
//            isEventBusRegistered = true
//            Log.d(TAG, "registerEventBus: ")
//        }
//    }

//    fun unregisterEventBus() {
//        if (isEventBusRegistered) {
//            EventBus.getDefault().unregister(this)
//            isEventBusRegistered = false
//            Log.d(TAG, "unregisterEventBus: unregister ")
//        }
//    }

//    @Subscribe
//    fun EventBusObserver(event: EventBusEvent) {
//        when (event) {
//            is EventBusEvent.CurrentLocationEvent -> {
//                setCurrentLocation(event.location)
//                Log.d(TAG, "EventBusObserver: ${event.location}")
//            }
//        }
//    }

    override fun onCleared() {
        // unregisterEventBus()
        Log.d(TAG, "unregisterEventBus: ")
        super.onCleared()
    }
} // End of WalkingRecordViewModel class
