package com.app.majuapp

import android.Manifest
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.app.majuapp.screen.culture.CultureDetailViewModel
import com.app.majuapp.screen.culture.CultureViewModel
import com.app.majuapp.screen.login.LoginViewModel
import com.app.majuapp.screen.login.SocialLoginViewModel
import com.app.majuapp.service.RecordingService
import com.app.majuapp.ui.theme.MajuAppTheme
import dagger.hilt.android.AndroidEntryPoint


private const val TAG = "MainActivity_창영"

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val socialLoginViewModel: SocialLoginViewModel by viewModels()
    private val loginViewModel: LoginViewModel by viewModels()
    private val cultureViewModel: CultureViewModel by viewModels()
    private val cultureDetailViewModel: CultureDetailViewModel by viewModels()

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

        setContent {
            MajuAppTheme {
                StatusBarColorChange()

                val navController = rememberNavController()
//                SetUpNavGraph(
//                    navController,
//                    socialLoginViewModel,
//                    loginViewModel,
//                    cultureViewModel,
//                    cultureDetailViewModel
//                )

                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = {
                            Intent(
                                applicationContext,
                                RecordingService::class.java,
                            ).also {
                                it.action = RecordingService.Actions.START.toString()
                                startService(it)
                            }
                        }
                    ) {
                        Text("start walk")
                    }

                    Spacer(modifier = Modifier.fillMaxWidth().height(40.dp))
                    Button(
                        onClick = {
                            Intent(
                                applicationContext,
                                RecordingService::class.java,
                            ).also {
                                it.action = RecordingService.Actions.STOP.toString()
                                startService(it)
                            }
                        }
                    ) {
                        Text("stop walk")
                    }
                }

            }
        }
    } // End of onCreate()


    override fun onResume() {
        super.onResume()
    }

} // End of MainActivity class

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
