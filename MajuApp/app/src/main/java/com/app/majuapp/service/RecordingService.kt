package com.app.majuapp.service

import android.R
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat

class RecordingService : Service() { // End of RunningService class

    override fun onBind(p0: Intent?): IBinder? {
        return null
    } // End of onBind()

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            Actions.START.toString() -> start()
            Actions.STOP.toString() -> stopSelf()
        }
        return super.onStartCommand(intent, flags, startId)
    } // End of onStartCommand()


    private fun start() {
        val notification = NotificationCompat.Builder(this, "walking_channel")



            .setSmallIcon(R.drawable.ic_notification_overlay).setContentTitle("산책중입니다!")
            .setContentText("Elapsed time").build()
        startForeground(1, notification)

    } // End of start()


    enum class Actions {
        START, STOP
    } // End of Actions class

} // End of RunningService class