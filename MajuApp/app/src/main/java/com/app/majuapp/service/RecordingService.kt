package com.app.majuapp.service

import android.R
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.IBinder
import android.util.Log
import androidx.compose.runtime.rememberCoroutineScope
import androidx.core.app.NotificationCompat
import com.app.majuapp.MainActivity
import com.app.majuapp.room.RecordingRepository
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


private const val TAG = "RecordingService_창영"

class RecordingService : Service(), SensorEventListener { // End of RunningService class


    @Inject
    lateinit var sensorManager: SensorManager

    @Inject
    lateinit var recordingRepository: RecordingRepository

    companion object {
        private val _stepsFlow: MutableStateFlow<Long> = MutableStateFlow(0L)
        val stepsFlow = _stepsFlow.asStateFlow()
    }


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


    private fun registerSensor() {
        val stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        sensorManager.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_UI)

    } // End of registerSensor()

    private fun start() {
        val intent = Intent(this, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        }
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent, PendingIntent.FLAG_MUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notification = NotificationCompat.Builder(this, "walking_channel")
            .setSmallIcon(R.drawable.ic_notification_overlay).setContentTitle("산책중입니다!")
            .setContentIntent(pendingIntent).setContentText("Elapsed time").build()
        startForeground(1, notification)
    } // End of start()

    override fun onSensorChanged(event: SensorEvent?) {
        if (event!!.values[0] > Int.MAX_VALUE || event.values[0] == 0f) return

        event.values?.firstOrNull()?.let { steps ->
            _stepsFlow.tryEmit(steps.toLong())
            Log.d(TAG, "onSensorChanged: $steps")
            // stepsFlow.value = steps.toLong()
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        Log.d(TAG, "onAccuracyChanged: ${sensor?.name}, $accuracy")
    }

    override fun onDestroy() {
        stopForeground(true)
        stopSelf()
        unregisterSensor()
        super.onDestroy()
    }

    private fun unregisterSensor() {
        try {
            sensorManager.unregisterListener(this)
        } catch (e: Exception) {
            Log.d(TAG, "unregisterSensor: ${e.message} ")
        }
    }

    enum class Actions {
        START, STOP
    } // End of Actions class
} // End of RunningService class