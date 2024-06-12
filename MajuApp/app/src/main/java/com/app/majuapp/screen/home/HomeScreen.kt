package com.app.majuapp.screen.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.app.majuapp.component.HomeBox

@Composable
fun HomeScreen(
    navController: NavController
) {

    HomeScreenContent()
} // End of HomeScreen()

@Composable
private fun HomeScreenContent() {

    Column(modifier = Modifier.fillMaxSize()) {
        HomeBox(modifier = Modifier)
    }


} // End of HomeScreenContent()