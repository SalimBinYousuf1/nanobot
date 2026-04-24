package com.college.attendance

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.college.attendance.ui.navigation.AttendanceApp
import com.college.attendance.ui.theme.CollegeAttendanceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CollegeAttendanceTheme {
                AttendanceApp()
            }
        }
    }
}
