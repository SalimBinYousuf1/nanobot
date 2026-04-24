package com.college.attendance.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.college.attendance.ui.screens.AttendanceScreen
import com.college.attendance.ui.screens.ClassesScreen
import com.college.attendance.ui.screens.DashboardScreen
import com.college.attendance.ui.screens.ProfileScreen
import com.college.attendance.ui.screens.ReportsScreen
import com.college.attendance.viewmodel.AttendanceViewModel

@Composable
fun AttendanceApp(vm: AttendanceViewModel = viewModel()) {
    val navController = rememberNavController()
    val items = listOf(AppScreen.Dashboard, AppScreen.Classes, AppScreen.Attendance, AppScreen.Reports, AppScreen.Profile)

    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                items.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = screen.title) },
                        label = { Text(screen.title) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = AppScreen.Dashboard.route,
            modifier = Modifier.padding(padding)
        ) {
            composable(AppScreen.Dashboard.route) { DashboardScreen(vm) }
            composable(AppScreen.Classes.route) { ClassesScreen(vm) }
            composable(AppScreen.Attendance.route) { AttendanceScreen(vm) }
            composable(AppScreen.Reports.route) { ReportsScreen(vm) }
            composable(AppScreen.Profile.route) { ProfileScreen(vm) }
        }
    }
}
