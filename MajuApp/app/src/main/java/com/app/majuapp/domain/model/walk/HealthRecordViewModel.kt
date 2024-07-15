package com.app.majuapp.domain.model.walk

import android.content.Context.SENSOR_SERVICE
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.lifecycle.ViewModel
import com.app.majuapp.Application
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class HealthRecordViewModel(
    application: Application
) : ViewModel(), SensorEventListener {

    private val sensorManager: SensorManager =
        application.getSystemService(SENSOR_SERVICE) as SensorManager
    private val stepSensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

    val _stepCount = MutableStateFlow(0)
    val stepCount = _stepCount.asStateFlow()

    fun updateStepCount(newStep: Int) {
        _stepCount.value = newStep
    } // End of updateStepCount()

    init {
        stepSensor?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            if (it.sensor.type == Sensor.TYPE_STEP_COUNTER) {
                _stepCount.value = it.values[0].toInt()
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Do nothing
    }

    override fun onCleared() {
        super.onCleared()
        sensorManager.unregisterListener(this)
    }

} // End of HealthRecordViewModel class