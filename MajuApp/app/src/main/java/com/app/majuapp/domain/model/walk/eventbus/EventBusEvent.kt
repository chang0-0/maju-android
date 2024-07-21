package com.app.majuapp.domain.model.walk.eventbus

import com.google.android.gms.maps.model.LatLng

sealed class EventBusEvent {
    data class CurrentLocationEvent(val location: LatLng) : EventBusEvent()
} // End of AppEvent class