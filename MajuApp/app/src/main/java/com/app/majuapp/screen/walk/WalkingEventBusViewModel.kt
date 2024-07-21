package com.app.majuapp.screen.walk

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WalkingEventBusViewModel @Inject constructor() : ViewModel() {

    fun currentLocation() {
//        viewModelScope.launch {
//            EventBusController.publishEvent()
//        }
    } // End of currentLocation()
} // End of WalkingEventBusViewModel class