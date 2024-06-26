package com.app.majuapp.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.automirrored.filled.Note
import androidx.compose.material.icons.automirrored.outlined.MenuBook
import androidx.compose.material.icons.automirrored.outlined.Note
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.DirectionsWalk
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.DirectionsWalk
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.MenuBook
import androidx.compose.material.icons.outlined.StarBorder
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

    data object Preference : Screen(
        route = "preference_screen",
        title = "Preference",
        selectedIcon = Icons.Filled.Star,
        unSelectedIcon = Icons.Outlined.StarBorder,
        onClick = null,
    )

    data object Culture : Screen(
        route = "culture_screen",
        title = "Culture",
        selectedIcon = Icons.AutoMirrored.Filled.Note,
        unSelectedIcon = Icons.AutoMirrored.Outlined.Note,
        onClick = null,
    )

    data object CultureDetail : Screen(
        route = "culture_detail_screen",
        title = "CultureDetail",
        selectedIcon = Icons.AutoMirrored.Filled.MenuBook,
        unSelectedIcon = Icons.AutoMirrored.Outlined.MenuBook,
        onClick = null,
    )

    data object CultureMap : Screen(
        route = "culture_map_screen",
        title = "CultureMap",
        selectedIcon = Icons.Filled.LocationOn,
        unSelectedIcon = Icons.Outlined.LocationOn,
        onClick = null,
    )

    data object Walk : Screen(
        route = "walk_screen",
        title = "Walk",
        selectedIcon = Icons.Filled.DirectionsWalk,
        unSelectedIcon = Icons.Outlined.DirectionsWalk,
        onClick = null,
    )

    data object Record : Screen(
        route = "record_screen",
        title = "Record",
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