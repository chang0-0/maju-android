package com.app.majuapp.domain.model.walk

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Settings

class CurrentLocationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val isTurnedOn = Settings.Global.getInt(
            context?.contentResolver,
            Settings.Global.AIRPLANE_MODE_ON
        ) != 0
    }
} // End of CurrentLocationReceiver class