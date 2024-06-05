package com.app.majuapp.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.app.majuapp.screen.HomeScreen
import com.app.majuapp.screen.MainScreen

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SetUpNavGraph(
    navController: NavHostController
) {
    val screenList = listOf(
        Screen.Home, Screen.Main
    )
    var selectedItemIndex by rememberSaveable {
        mutableStateOf(0)
    }

    SharedTransitionLayout {
        Scaffold(
            bottomBar = {
                NavigationBar() {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination

                    screenList.forEachIndexed { index, screen ->
                        NavigationBarItem(
                            selected = selectedItemIndex == index,
                            onClick = {
                                selectedItemIndex = index
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            label = {
                                Text(text = screen.title)
                            },
                            alwaysShowLabel = false,
                            icon = {
                                Icon(
                                    imageVector = if (index == selectedItemIndex) {
                                        screen.selectedIcon!!
                                    } else {
                                        screen.unSelectedIcon!!
                                    }, contentDescription = screen.title
                                )
                            }
                        )
                    }
                }


            }
        ) { paddingValues ->
            NavHost(
                navController = navController,
                startDestination = Screen.Home.route,
                modifier = Modifier.padding(paddingValues)
            ) {

                composable(
                    route = Screen.Home.route
                ) {
                    HomeScreen(navController = navController)
                }

                composable(
                    route = Screen.Main.route
                ) {
                    MainScreen(navController = navController)
                }
            }
        }
    } // SharedTransitionLayout
} // End of SetUpNavGraph