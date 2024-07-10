package com.app.majuapp

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.app.majuapp.navigation.SetUpNavGraph
import com.app.majuapp.ui.theme.MajuAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MajuAppTheme {
                StatusBarColorChange()
                val navController = rememberNavController()
                SetUpNavGraph(navController)
            }
        }
    } // End of onCreate()
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