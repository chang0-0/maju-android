package com.app.majuapp.screen.culture

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.app.majuapp.component.CultureCard
import com.app.majuapp.component.RowChoiceChips
import com.app.majuapp.data.model.CultureModel
import com.app.majuapp.navigation.Screen
import com.app.majuapp.screen.MainScreen
import com.app.majuapp.screen.home.HomeScreen
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