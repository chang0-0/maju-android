package com.app.majuapp.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
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
import com.app.majuapp.screen.MainScreen
import com.app.majuapp.screen.culture.CultureDetailScreen
import com.app.majuapp.screen.culture.CultureMapScreen
import com.app.majuapp.screen.culture.CultureScreen
import com.app.majuapp.screen.home.HomeScreen
import com.app.majuapp.screen.login.LoginScreen
import com.app.majuapp.screen.login.LoginViewModel
import com.app.majuapp.screen.login.SocialLoginViewModel
import com.app.majuapp.screen.preference.PreferenceScreen
import com.app.majuapp.screen.record.RecordScreen
import com.app.majuapp.screen.test.TestScreen
import com.app.majuapp.screen.walk.WalkScreen
import com.app.majuapp.ui.theme.SonicSilver
import com.app.majuapp.ui.theme.SpiroDiscoBall

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SetUpNavGraph(
    navController: NavHostController,
    socialLoginViewModel: SocialLoginViewModel,
    loginViewModel: LoginViewModel,
) {
    val screenList = listOf(
        Screen.CultureMap, Screen.Culture
    )
    var selectedItemIndex by rememberSaveable {
        mutableStateOf(0)
    }

//    SharedTransitionLayout {
//        NavHost(
//            navController = navController,
//            startDestination = Screen.Preference.route,
//        ) {
//            composable(
//                route = Screen.Home.route
//            ) {
//                HomeScreen(navController = navController)
//            }
//
//            composable(
//                route = Screen.Main.route
//            ) {
//                MainScreen(navController = navController)
//            }
//
//            composable(
//                route = Screen.Preference.route
//            ) {
//                PreferenceScreen(navController = navController)
//            }
//
//            composable(
//                route = Screen.Culture.route
//            ) {
//                CultureScreen(navController = navController)
//            }
//
//            composable(
//                route = Screen.Walk.route
//            ) {
//                WalkScreen(navController = navController)
//            }
//
//            composable(
//                route = Screen.Test.route
//            ) {
//                TestScreen(navController = navController)
//            }
//        }

    Scaffold(
        bottomBar = {
            if (shouldShowBottomBar(navController = navController))
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
                                    },
                                    contentDescription = screen.title,
                                    tint = if (index == selectedItemIndex) SpiroDiscoBall else SonicSilver

                                )
                            }
                        )
                    }
                }


        }
    ) { paddingValues ->
//        SharedTransitionLayout {
        NavHost(
            navController = navController,
            startDestination = Screen.Login.route,
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

            composable(
                route = Screen.Preference.route
            ) {
                PreferenceScreen(navController = navController)
            }

            composable(
                route = Screen.Culture.route
            ) {
                CultureScreen(navController = navController)
            }

            composable(
                route = Screen.CultureDetail.route
            ) {
                CultureDetailScreen(navController = navController)
            }

            composable(
                route = Screen.CultureMap.route
            ) {
                CultureMapScreen(navController = navController)
            }

            composable(
                route = Screen.Walk.route
            ) {
                WalkScreen(navController = navController)
            }

            composable(
                route = Screen.Test.route
            ) {
                TestScreen(navController = navController)
            }

            composable(
                route = Screen.Login.route
            ) {
                LoginScreen(navController = navController, socialLoginViewModel = socialLoginViewModel, loginViewModel = loginViewModel)
            }

            composable(route = Screen.Record.route) {
                RecordScreen(navController = navController)
            }

        } // NavHost
//        } // SharedTransitionLayout
    }
} // End of SetUpNavGraph

@Composable
fun shouldShowBottomBar(navController: NavHostController): Boolean {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return when (navBackStackEntry?.destination?.route) {
        Screen.CultureMap.route, Screen.Culture.route -> true
        else -> false
    }
}