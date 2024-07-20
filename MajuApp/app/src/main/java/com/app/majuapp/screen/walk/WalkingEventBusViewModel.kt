package com.app.majuapp.screen.walk

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.majuapp.domain.model.walk.eventbus.AppEvent
import com.app.majuapp.domain.model.walk.eventbus.EventBusController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WalkingEventBusViewModel @Inject constructor() : ViewModel() {

    fun currentLocation() {
        viewModelScope.launch {
            EventBusController.publishEvent(AppEvent.LOCATION)
        }
    } // End of currentLocation()

} // End of WalkingEventBusViewModel class