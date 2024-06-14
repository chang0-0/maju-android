package com.app.majuapp.screen.walk

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.app.majuapp.R
import com.app.majuapp.component.WalkScreenChooseStartDialog

@Composable
fun WalkScreen(navController: NavController) {
    WalkScreenContent(navController)
} // End of WalkScreen()

@Composable
private fun WalkScreenContent(navController: NavController) {
    val context = LocalContext.current

    WalkScreenChooseStartDialog(
        context.getString(R.string.walk_screen_choose_start_dialog_title),
        context.getString(R.string.walk_screen_choose_start_dialog_content),
        {},
        {})
} // End of WalkScreenContent()
