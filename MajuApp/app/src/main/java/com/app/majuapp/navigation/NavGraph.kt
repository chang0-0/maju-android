package com.app.majuapp.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.app.majuapp.screen.MainScreen
import com.app.majuapp.screen.culture.CultureDetailScreen
import com.app.majuapp.screen.culture.CultureDetailViewModel
import com.app.majuapp.screen.culture.CultureMapScreen
import com.app.majuapp.screen.culture.CultureScreen
import com.app.majuapp.screen.culture.CultureViewModel
import com.app.majuapp.screen.home.HomeScreen
import com.app.majuapp.screen.home.HomeViewModel
import com.app.majuapp.screen.login.LoginScreen
import com.app.majuapp.screen.login.LoginViewModel
import com.app.majuapp.screen.login.SocialLoginViewModel
import com.app.majuapp.screen.preference.PreferenceScreen
import com.app.majuapp.screen.record.RecordScreen
import com.app.majuapp.screen.test.TestScreen
import com.app.majuapp.screen.walk.WalkScreen
import com.app.majuapp.screen.webview.WebViewScreen
import com.app.majuapp.ui.theme.SonicSilver
import com.app.majuapp.ui.theme.SpiroDiscoBall

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SetUpNavGraph(
    navController: NavHostController,
    socialLoginViewModel: SocialLoginViewModel,
    loginViewModel: LoginViewModel,
    cultureViewModel: CultureViewModel,
    cultureDetailViewModel: CultureDetailViewModel,
    homeViewModel: HomeViewModel
) {
    val screenList = listOf(
        Screen.CultureMap, Screen.Culture
    )
    var selectedItemIndex by remember {
        mutableStateOf(0)
    }

    Scaffold(
        bottomBar = {
            if (shouldShowBottomBar(navController = navController)) {
                NavigationBar() {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination

                    screenList.forEachIndexed { index, screen ->
                        var selected = currentDestination?.hierarchy?.any {
                            it.route == screen.route
                        } == true
                        NavigationBarItem(
                            selected = selected,
                            onClick = {
                                navController.navigate(screen.route) {
                                    popUpTo(screen.route)
                                    launchSingleTop = true
                                }
                            },
                            label = {
                                Text(text = screen.title)
                            },
                            alwaysShowLabel = false,
                            icon = {
                                Icon(
                                    imageVector = if (selected) {
                                        screen.selectedIcon!!
                                    } else {
                                        screen.unSelectedIcon!!
                                    },
                                    contentDescription = screen.title,
                                    tint = if (selected) SpiroDiscoBall else SonicSilver

                                )
                            }
                        )
                    }
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
                HomeScreen(navController = navController, homeViewModel = homeViewModel)
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
                CultureScreen(navController = navController, cultureViewModel = cultureViewModel)
            }

            composable(
                route = "${Screen.CultureDetail.route}/{id}",
                arguments = listOf(navArgument("id") {
                    type = NavType.IntType
                })
            ) {
                val cultureEventId = it.arguments?.getInt("id") ?: 0
                CultureDetailScreen(
                    navController = navController,
                    cultureEventId,
                    cultureDetailViewModel
                )
            }

            composable(
                route = Screen.CultureMap.route
            ) {
                CultureMapScreen(
                    navController = navController,
                    cultureViewModel = cultureViewModel,
                    navigateToCultureScreen = {

                    },
                    onBack = {
//                        selectedItemIndex = 0
                        navController.popBackStack()
                    }
                )
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
                LoginScreen(
                    navController = navController,
                    socialLoginViewModel = socialLoginViewModel,
                    loginViewModel = loginViewModel
                )
            }

            composable(route = Screen.Record.route) {
                RecordScreen(navController = navController)
            }

            composable(
                route = "${Screen.WebView.route}"
            ) {
                WebViewScreen(navController = navController, cultureDetailViewModel)
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