package com.college.attendance.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.ui.graphics.vector.ImageVector

sealed class AppScreen(val route: String, val title: String, val icon: ImageVector) {
    data object Dashboard : AppScreen("dashboard", "Home", Icons.Default.Dashboard)
    data object Classes : AppScreen("classes", "Classes", Icons.Default.MenuBook)
    data object Attendance : AppScreen("attendance", "Mark", Icons.Default.Assignment)
    data object Reports : AppScreen("reports", "Reports", Icons.Default.BarChart)
    data object Profile : AppScreen("profile", "Profile", Icons.Default.AccountCircle)
}
