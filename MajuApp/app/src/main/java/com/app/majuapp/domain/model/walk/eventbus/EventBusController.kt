package com.app.majuapp.domain.model.walk.eventbus

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

object EventBusController {

    private val _locationEventBus = MutableSharedFlow<AppEvent>()
    val locationEventBus = _locationEventBus.asSharedFlow()

    suspend fun publishEvent(appEvent: AppEvent) {
        _locationEventBus.emit(appEvent)
    } // End of publishEvent()

} // End of EventBusController class