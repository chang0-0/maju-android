package com.app.majuapp.domain.model.walk.eventbus

import android.util.Log
import org.greenrobot.eventbus.Subscribe

private const val TAG = "EventBusScriber_창영"


class EventBusScriber {
    @Subscribe
    fun onMessage(event: EventBusEvent) {
        Log.d(TAG, "onMessage: $event")
    }
}