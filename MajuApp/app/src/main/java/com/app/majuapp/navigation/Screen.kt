package com.app.majuapp.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.DirectionsWalk
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.DirectionsWalk
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(
    val route: String,
    val title: String,
    val selectedIcon: ImageVector?,
    val unSelectedIcon: ImageVector?,
    val onClick: Unit?
) {
    data object Home : Screen(
        route = "home_screen",
        title = "Home",
        selectedIcon = Icons.Filled.Home,
        unSelectedIcon = Icons.Outlined.Home,
        onClick = null
    )

    data object Main : Screen(
        route = "main_screen",
        title = "Main",
        selectedIcon = Icons.Filled.Bookmark,
        unSelectedIcon = Icons.Outlined.BookmarkBorder,
        onClick = null,
    )

    data object Walk : Screen(
        route = "walk_screen",
        title = "Walk",
        selectedIcon = Icons.Filled.DirectionsWalk,
        unSelectedIcon = Icons.Outlined.DirectionsWalk,
        onClick = null,
    )

    data object Test : Screen(
        route = "test_screen",
        title = "Test",
        selectedIcon = Icons.Filled.DirectionsWalk,
        unSelectedIcon = Icons.Outlined.DirectionsWalk,
        onClick = null,
    )
}// End of Screen class