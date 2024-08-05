package com.example.idoor.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Construction
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.idoor.R
import com.example.idoor.navigation.Screen
import com.example.idoor.ui.theme.CharcoalGray
import com.example.idoor.ui.theme.LightGray
import com.example.idoor.ui.theme.MidnightBlue

@Composable
fun BottomBarUser(
    navController: NavHostController
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    BottomAppBar(
        modifier = Modifier
            .windowInsetsPadding(WindowInsets.navigationBars)
            .padding(horizontal = 10.dp)
            .clip(RoundedCornerShape(16.dp))
    ) {
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = stringResource(R.string.menu_home)
                )
            },
            label = { Text(stringResource(R.string.menu_home)) },
            selected = currentRoute == Screen.DashboardUser.route,
            colors = NavigationBarItemDefaults.colors(
                unselectedIconColor = CharcoalGray,
                unselectedTextColor = CharcoalGray,
                selectedIconColor = LightGray,
                indicatorColor = MidnightBlue,
            ),
            onClick = {
                navController.navigate(Screen.DashboardUser.route) {
                    navController.graph.findNode(Screen.DashboardUser.route)?.let {
                        popUpTo(it.id) {
                            saveState = true
                        }
                    }
                    restoreState = true
                    launchSingleTop = true
                }
            }
        )
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Construction,
                    contentDescription = stringResource(R.string.menu_kelola_alat)
                )
            },
            label = { Text(stringResource(R.string.menu_kelola_alat)) },
            selected = currentRoute == Screen.ManageUserTools.route,
            colors = NavigationBarItemDefaults.colors(
                unselectedIconColor = CharcoalGray,
                unselectedTextColor = CharcoalGray,
                selectedIconColor = LightGray,
                indicatorColor = MidnightBlue,
            ),
            onClick = {
                navController.navigate(Screen.ManageUserTools.route) {
                    navController.graph.findNode(Screen.DashboardUser.route)?.let {
                        popUpTo(it.id) {
                            saveState = true
                        }
                    }
                    restoreState = true
                    launchSingleTop = true
                }
            }
        )
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.History,
                    contentDescription = stringResource(R.string.menu_history_akses)
                )
            },
            label = { Text(stringResource(R.string.menu_history_akses)) },
            selected = currentRoute == Screen.HistoryAccess.route,
            colors = NavigationBarItemDefaults.colors(
                unselectedIconColor = CharcoalGray,
                unselectedTextColor = CharcoalGray,
                selectedIconColor = LightGray,
                indicatorColor = MidnightBlue,
            ),
            onClick = {
                navController.navigate(Screen.HistoryAccess.route) {
                    navController.graph.findNode(Screen.DashboardUser.route)?.let {
                        popUpTo(it.id) {
                            saveState = true
                        }
                    }
                    restoreState = true
                    launchSingleTop = true
                }
            }
        )
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = stringResource(R.string.menu_profile)
                )
            },
            label = { Text(stringResource(R.string.menu_profile)) },
            selected = currentRoute == Screen.Profile.route,
            colors = NavigationBarItemDefaults.colors(
                unselectedIconColor = CharcoalGray,
                unselectedTextColor = CharcoalGray,
                selectedIconColor = LightGray,
                indicatorColor = MidnightBlue,
            ),
            onClick = {
                navController.navigate(Screen.Profile.route) {
                    navController.graph.findNode(Screen.DashboardUser.route)?.let {
                        popUpTo(it.id) {
                            saveState = true
                        }
                    }
                    restoreState = true
                    launchSingleTop = true
                }
            }
        )
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4_XL)
@Composable
fun BottomBarUserPreview() {
    val navController: NavHostController = rememberNavController()
    Box (
        modifier = Modifier
            .background(MidnightBlue)
            .fillMaxWidth()
    ){
        BottomBarUser(navController = navController)
    }
}