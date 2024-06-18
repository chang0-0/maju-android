package com.app.majuapp.screen.walk

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.app.majuapp.R
import com.app.majuapp.component.WalkScreenChooseStartDialog
import kotlinx.coroutines.launch

@Composable
fun WalkScreen(navController: NavController) {
    WalkScreenContent(navController)
} // End of WalkScreen()

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun WalkScreenContent(navController: NavController) {
    val context = LocalContext.current
    var showDialog = remember { mutableStateOf(true) }


    /* BottomSheet*/
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)
    val scaffoldState = rememberBottomSheetScaffoldState()


    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            BottomSheetScaffold(
                scaffoldState = scaffoldState,
                sheetPeekHeight = 60.dp,
                sheetContainerColor = Color.White,
                sheetContent = {
                    Column(
                        Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            Modifier.fillMaxWidth().height(128.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("Swipe up to expand sheet")
                        }
                        Text("Sheet content")
                        Button(
                            modifier = Modifier.padding(bottom = 64.dp),
                            onClick = { scope.launch { scaffoldState.bottomSheetState.partialExpand() } }
                        ) {
                            Text("Click to collapse sheet")
                        }
                    }
                }
            ) { innerPadding ->

            }
        }
    }

    if (showDialog.value) {
        WalkScreenChooseStartDialog(
            context.getString(R.string.walk_screen_dialog_choose_promenade_title),
            context.getString(R.string.walk_screen_dialog_choose_promenade_content),
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
