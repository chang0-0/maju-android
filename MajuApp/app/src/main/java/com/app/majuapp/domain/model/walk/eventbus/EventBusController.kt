package com.app.majuapp.domain.model.walk.eventbus

import android.util.Log
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

private const val TAG = "EventBusController_창영"

object EventBusController {

    /* EventBus */

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onCurrentLocationEvent(event: EventBusEvent): EventBusEvent {
        when (event) {
            is EventBusEvent.CurrentLocationEvent -> {
                Log.d(TAG, "walkScreen -> onCurrentLocationEvent: $event")
                return event
            }
        }
    }


} // End of EventBusController class