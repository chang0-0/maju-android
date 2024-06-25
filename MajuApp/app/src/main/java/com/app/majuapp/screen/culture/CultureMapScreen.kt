package com.app.majuapp.screen.culture

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.app.majuapp.component.CultureCard
import com.app.majuapp.util.dummyList

@Composable
fun CultureMapScreen(navController: NavHostController = rememberNavController()) {

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 40.dp, start = 24.dp, end = 24.dp)
    ) {
        CultureCard(dummyList[0], false)
    }

} // End of CultureMapScreen

@Preview
@Composable
fun PreviewCultureMapScreen() {
    CultureMapScreen()
} // End of PreviewCultureMapScreen