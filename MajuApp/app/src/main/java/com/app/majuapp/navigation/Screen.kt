package com.app.majuapp.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Home
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
}// End of Screen class