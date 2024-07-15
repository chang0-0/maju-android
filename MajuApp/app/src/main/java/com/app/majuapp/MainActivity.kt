package com.app.majuapp

import android.Manifest
import android.content.Context
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.app.majuapp.navigation.SetUpNavGraph
import com.app.majuapp.screen.culture.CultureDetailViewModel
import com.app.majuapp.screen.culture.CultureViewModel
import com.app.majuapp.screen.home.HomeViewModel
import com.app.majuapp.screen.login.LoginViewModel
import com.app.majuapp.screen.login.SocialLoginViewModel
import com.app.majuapp.screen.walk.WalkingRecordViewModel
import com.app.majuapp.ui.theme.MajuAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


private const val TAG = "MainActivity_창영"

@AndroidEntryPoint
class MainActivity : ComponentActivity(), SensorEventListener { // End of MainActivity class

    private val homeViewModel: HomeViewModel by viewModels()
    private val loginViewModel: LoginViewModel by viewModels()
    private val cultureViewModel: CultureViewModel by viewModels()
    private val socialLoginViewModel: SocialLoginViewModel by viewModels()
    private val cultureDetailViewModel: CultureDetailViewModel by viewModels()
    private val walkingRecordViewModel: WalkingRecordViewModel by viewModels()

    private lateinit var sensorManager: SensorManager
    private val _stepCount = MutableStateFlow(0)
    val stepCount: StateFlow<Int> = _stepCount


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.POST_NOTIFICATIONS,
                ), 0
            )
        }

        sensorManager =
            application.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val stepSensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

        stepSensor?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI)
        }

        Log.d(TAG, "onCreate: $stepSensor")


//        sensorManager = (getSystemService(Context.SENSOR_SERVICE) as SensorManager?)!!
//        val sensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
//        sensor?.let {
//            sensorManager.registerListener(
//                this@MainActivity,
//                it,
//                SensorManager.SENSOR_DELAY_FASTEST
//            )
//        }


        setContent {
            MajuAppTheme {
                StatusBarColorChange()

                val navController = rememberNavController()
                SetUpNavGraph(
                    navController,
                    socialLoginViewModel,
                    loginViewModel,
                    cultureViewModel,
                    cultureDetailViewModel
                )

//                Column(
//                    modifier = Modifier.fillMaxSize(),
//                    horizontalAlignment = Alignment.CenterHorizontally,
//                    verticalArrangement = Arrangement.Center
//                ) {
//                    Button(
//                        onClick = {
//                            Intent(
//                                applicationContext,
//                                RecordingService::class.java,
//                            ).also {
//                                it.action = RecordingService.Actions.START.toString()
//                                startService(it)
//                            }
//                        }
//                    ) {
//                        Text("start walk")
//                    }
//
//                    Spacer(modifier = Modifier.fillMaxWidth().height(40.dp))
//                    Button(
//                        onClick = {
//                            Intent(
//                                applicationContext,
//                                RecordingService::class.java,
//                            ).also {
//                                it.action = RecordingService.Actions.STOP.toString()
//                                startService(it)
//                            }
//                        }
//                    ) {
//                        Text("stop walk")
//                    }
//                }

            }
        }
    } // End of onCreate()


    override fun onResume() {
        super.onResume()
    }

    override fun onStop() {
        super.onStop()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            if (it.sensor.type == Sensor.TYPE_STEP_COUNTER) {
                _stepCount.value = it.values[0].toInt()
                val steps = it.values[0].toInt()


                _stepCount.value = steps
                walkingRecordViewModel.setStepCount(steps)
                walkingRecordViewModel.setTodayStepCount(steps)

                val todayStepCount = steps
                walkingRecordViewModel.setTodayStepCount(todayStepCount)
                Log.d(TAG, "MainActivity -> todayStepCount: ${todayStepCount}")
                Log.d(TAG, "MainActivity onSensorChanged: ${_stepCount.value}")
            }
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        // Do nothing
    }
}

@Composable
fun StatusBarColorChange() {
    // 상태바 컬러 변경
    val activity = LocalContext.current as ComponentActivity
    SideEffect {
        WindowCompat.getInsetsController(
            activity.window,
            activity.window.decorView
        ).isAppearanceLightStatusBars = true
        activity.window.statusBarColor = Color.TRANSPARENT
    }
} // End of StatusBarColorChange()
