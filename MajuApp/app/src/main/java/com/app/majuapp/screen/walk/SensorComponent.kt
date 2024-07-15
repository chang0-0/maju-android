package com.app.majuapp.screen.walk

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext

private const val TAG = "SensorComponent_창영"

class SensorComponent {
//    val context = LocalContext.current
//
//    val sensorManager: SensorManager =
//        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
//    lateinit var sensor: Sensor
//
//    // val stepCoutnerSensor = sensorManager.
//    val stepCounterSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
//    var stepCount = 0
//
//    override fun onSensorChanged(sensorEvent: SensorEvent?) {
//        if (sensorEvent!!.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
//            stepCount = sensorEvent.values[0] as Int
//
//        }
//    }
//
//    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
//        TODO("Not yet implemented")
//    }





} // End of SensorComponent class