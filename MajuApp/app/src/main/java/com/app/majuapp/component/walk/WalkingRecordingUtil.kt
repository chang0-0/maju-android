package com.app.majuapp.component.walk

import android.content.Context
import android.content.Context.SENSOR_SERVICE
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.location.Location
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.app.majuapp.screen.walk.WalkingRecordViewModel

private const val TAG = "WalkingRecordingUtil_창영"

@Composable
fun StepCounterSensorManager(
    walkingRecordViewModel: WalkingRecordViewModel
) {
    val context = LocalContext.current
    val sensorManager = remember {
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    var stepCount by remember { mutableFloatStateOf(0f) }

    val stepCounterSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
    val sensorEventListener = remember {
        object : SensorEventListener {
            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
                // 정확도 변경 처리
            }

            override fun onSensorChanged(event: SensorEvent) {
                // 걸음 수 업데이트 처리
                stepCount = event.values[0]
                walkingRecordViewModel.setStepCount(stepCount.toInt())
            }
        }
    }

    DisposableEffect(Unit) {
        stepCounterSensor?.let {
            sensorManager.registerListener(
                sensorEventListener, it, SensorManager.SENSOR_DELAY_FASTEST
            )
        }

        onDispose {
            sensorManager.unregisterListener(sensorEventListener)
        }
    }
} // End of rememberStepCounterSensorState()


@Composable
fun BearingSensorManager(context: Context, walkingRecordViewModel: WalkingRecordViewModel) {

    var location by remember { mutableStateOf<Location?>(null) }
    var azimuth by remember { mutableFloatStateOf(0f) }
    // Sensor manager to detect device orientation
    val sensorManager = remember { context.getSystemService(SENSOR_SERVICE) as SensorManager }
    val sensorEventListener = remember {
        object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent?) {
                if (event?.sensor?.type == Sensor.TYPE_ROTATION_VECTOR) {
                    val rotationMatrix = FloatArray(9)
                    SensorManager.getRotationMatrixFromVector(rotationMatrix, event.values)
                    val orientationValues = FloatArray(3)
                    SensorManager.getOrientation(rotationMatrix, orientationValues)
                    azimuth = Math.toDegrees(orientationValues[0].toDouble()).toFloat()
                    walkingRecordViewModel.setAzimuth(azimuth.toInt())
                }
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
        }
    }

    DisposableEffect(Unit) {
        sensorManager.registerListener(
            sensorEventListener,
            sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR),
            SensorManager.SENSOR_DELAY_UI
        )
        onDispose {
            sensorManager.unregisterListener(sensorEventListener)
        }
    }


} // End of BearingSensorManager