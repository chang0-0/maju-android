package com.app.majuapp

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.app.majuapp.navigation.SetUpNavGraph
import com.app.majuapp.screen.culture.CultureDetailViewModel
import com.app.majuapp.screen.culture.CultureViewModel
import com.app.majuapp.screen.login.LoginViewModel
import com.app.majuapp.screen.login.SocialLoginViewModel
import com.app.majuapp.ui.theme.MajuAppTheme
import com.app.majuapp.util.checkAndRequestPermissions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val socialLoginViewModel: SocialLoginViewModel by viewModels()
    private val loginViewModel: LoginViewModel by viewModels()
    private val cultureViewModel: CultureViewModel by viewModels()
    private val cultureDetailViewModel: CultureDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            MajuAppTheme {
                StatusBarColorChange()

                val navController = rememberNavController()
                SetUpNavGraph(navController, socialLoginViewModel, loginViewModel, cultureViewModel, cultureDetailViewModel)
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