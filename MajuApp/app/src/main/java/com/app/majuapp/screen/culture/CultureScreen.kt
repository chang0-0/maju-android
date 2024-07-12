package com.app.majuapp.screen.culture

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.app.majuapp.component.culture.CultureCard
import com.app.majuapp.component.RowChoiceChips
import com.app.majuapp.navigation.Screen
import com.app.majuapp.util.dummyCultureCategories
import com.app.majuapp.util.dummyList

@Composable
fun CultureScreen(navController: NavHostController) {

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 40.dp, start = 24.dp, end = 24.dp)
    ) {
        //TODO Replace real Data

        Column {
            RowChoiceChips(dummyCultureCategories)

            LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                items(dummyList) { culture ->
                    CultureCard(culture) { navController.navigate(Screen.CultureDetail.route) }
                }
            }

        }

    }


} // End of CultureScreen

@Preview
@Composable
fun PreviewCultureScreen() {
    CultureScreen(navController = rememberNavController())
}