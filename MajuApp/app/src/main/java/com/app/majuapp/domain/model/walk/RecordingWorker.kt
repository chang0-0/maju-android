package com.app.majuapp.domain.model.walk

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ForegroundInfo
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.app.majuapp.room.RecordingRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

private const val TAG = "RecordingWorker_창영"

class RecordingWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted params: WorkerParameters
) : CoroutineWorker(appContext = appContext, params) {


    @Inject
    lateinit var recordingRepository: RecordingRepository

    override suspend fun doWork(): Result {
        setForeground(getForegroundInfo())

        val sensorManager =
            applicationContext.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val stepCounterSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

        return if (stepCounterSensor != null) {
            val success = getStepCount(sensorManager, stepCounterSensor)
            if (success) {
                Result.success()
            } else {
                Result.retry()
            }
        } else {
            Result.failure()
        }
    } // End of doWork()

    private suspend fun getStepCount(
        sensorManager: SensorManager,
        stepCounterSensor: Sensor,
    ) = suspendCoroutine<Boolean> { continuation ->
        val listener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent?) {
                sensorManager.unregisterListener(this)

                event?.values?.firstOrNull()?.let { steps ->
                    // val stepsToday = recordingRepository.insertStepCount(steps.toLong())
                    continuation.resume(true)
                    return
                }

                continuation.resume(false)
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
                Log.d(TAG, "onAccuracyChanged: ")
            }
        }

        sensorManager.registerListener(
            listener,
            stepCounterSensor,
            SensorManager.SENSOR_DELAY_NORMAL
        )
    }

    override suspend fun getForegroundInfo(): ForegroundInfo {
        val title = "step_count"
        val content = "Obtaining"
        val notification =
            NotificationCompat.Builder(applicationContext, "walking_channel")
                .setContentTitle("walking_channel").setOngoing(true).build()

        return ForegroundInfo(WORKER_NOTIFY_ID, notification)
    }

    fun periodWorker(context: Context) {
        val workRequest = PeriodicWorkRequestBuilder<RecordingWorker>(
            15, TimeUnit.MINUTES
        ).addTag(TAG).build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "worker",
            ExistingPeriodicWorkPolicy.REPLACE,
            workRequest
        )
    } // End of periodWorker()

    companion object {
        private const val WORKER_NOTIFY_ID = 2
    }
} // End of RecordingWorker class