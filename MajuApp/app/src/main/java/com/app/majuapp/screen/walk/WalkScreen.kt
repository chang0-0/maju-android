package com.app.majuapp.screen.walk

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.app.majuapp.R
import com.app.majuapp.component.PartialBottomSheet
import com.app.majuapp.component.WalkScreenChooseStartDialog

@Composable
fun WalkScreen(navController: NavController) {
    WalkScreenContent(navController)
} // End of WalkScreen()

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun WalkScreenContent(navController: NavController) {
    val context = LocalContext.current
    var showDialog = remember { mutableStateOf(true) }
    var lastVisibleIndex by remember { mutableStateOf(0) }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            PartialBottomSheet()
            Button(modifier = Modifier.width(100.dp), onClick = {
                showDialog.value = true
            }) {
                Text(text = "Show Dialog")
            }
        }
    }

    if (showDialog.value) {
        WalkScreenChooseStartDialog(
            context.getString(R.string.walk_screen_choose_start_dialog_title),
            context.getString(R.string.walk_screen_choose_start_dialog_content),
            onClickDismiss = {
                showDialog.value = false
                navController.popBackStack()
            },
            onClickConfirm = {
                showDialog.value = false
            }
        )
    }
} // End of WalkScreenContent()
