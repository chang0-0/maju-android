package com.app.majuapp.screen.culture

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.app.majuapp.component.CultureCard
import com.app.majuapp.data.model.CultureModel
import com.app.majuapp.navigation.Screen
import com.app.majuapp.screen.MainScreen
import com.app.majuapp.screen.home.HomeScreen

@Composable
fun CultureScreen(navController: NavHostController) {

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 40.dp, start = 24.dp, end = 24.dp)
    ){
        //TODO Replace real Data
        val dummyList = listOf(
            CultureModel(
            0,
            "뮤지컬/오페라",
            "https://cdn.woman.chosun.com/news/photo/202309/112221_118277_4824.jpg",
            "오페라 갈라",
            "세종 대극장",
            "2024-12-07"
        ), CultureModel(
                0,
                "클래식",
                "https://cdn.woman.chosun.com/news/photo/202309/112221_118277_4824.jpg",
                "송년음악회",
                "세종 대극장",
                "2024-12-07"
            ), CultureModel(
                0,
                "축제",
                "https://cdn.woman.chosun.com/news/photo/202309/112221_118277_4824.jpg",
                "오페라 갈라",
                "세종 대극장",
                "2024-12-07"
            ), CultureModel(
                0,
                "뮤지컬/오페라",
                "https://cdn.woman.chosun.com/news/photo/202309/112221_118277_4824.jpg",
                "오페라 갈라",
                "세종 대극장",
                "2024-12-07"
            ), CultureModel(
                0,
                "뮤지컬/오페라",
                "https://cdn.woman.chosun.com/news/photo/202309/112221_118277_4824.jpg",
                "오페라 갈라",
                "세종 대극장",
                "2024-12-07"
            ), CultureModel(
                0,
                "뮤지컬/오페라",
                "https://cdn.woman.chosun.com/news/photo/202309/112221_118277_4824.jpg",
                "오페라 갈라",
                "세종 대극장",
                "2024-12-07"
            )
        )

        LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            items(dummyList) { culture ->
                CultureCard(culture)
            }
        }
    }


} // End of CultureScreen