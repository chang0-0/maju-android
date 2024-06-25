package com.app.majuapp.screen.test

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.app.majuapp.R
import com.app.majuapp.ui.theme.White
import kotlinx.coroutines.launch

@Composable
fun TestScreen(navController: NavController) {
    TestScreenContent(navController)
} // End of TestScreen()

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TestScreenContent(navController: NavController) {

    /* BottomSheet*/
    val sheetState = rememberModalBottomSheetState()
    var isSheetOpen by rememberSaveable {
        mutableStateOf(false)
    }
    val scaffoldState = rememberBottomSheetScaffoldState()
    val bottomSheetScope = rememberCoroutineScope()

    Surface(modifier = Modifier.fillMaxSize().background(White)) {
        BottomSheetScaffold(
            scaffoldState = scaffoldState,
            sheetContent = {
                Image(
                    painter = painterResource(id = R.drawable.tokyo_test_image),
                    contentDescription = null
                )
            }, sheetPeekHeight = 120.dp
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Button(modifier = Modifier.height(40.dp).wrapContentWidth(), onClick = {
                    bottomSheetScope.launch {
                        scaffoldState.bottomSheetState.expand()
                    }
                }) {
                    Text("바텀 시트 활성화")
                }
            }
        }

//        if (isSheetOpen) {
//            ModalBottomSheet(sheetState = sheetState, onDismissRequest = {
//                isSheetOpen = false
//                sheetState.isVisible
//            }) {
//                Image(
//                    painter = painterResource(id = R.drawable.tokyo_test_image),
//                    contentDescription = null
//                )
//            }
//        }


    }

} // End of TestScreenContent()