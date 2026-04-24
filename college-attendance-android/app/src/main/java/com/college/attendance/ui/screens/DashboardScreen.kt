package com.college.attendance.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.college.attendance.viewmodel.AttendanceViewModel

@Composable
fun DashboardScreen(vm: AttendanceViewModel) {
    val state by vm.uiState.collectAsState()
    val alerts by vm.alerts.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text("College Attendance Manager", style = MaterialTheme.typography.headlineSmall)
            Text("One app for faculty, admin, students and parents.")
        }

        item {
            StatsCard("Students", state.students.size.toString())
        }

        item {
            StatsCard("Courses", state.courses.size.toString())
        }

        item {
            Text("Low attendance alerts", style = MaterialTheme.typography.titleMedium)
        }

        if (alerts.isEmpty()) {
            item { Text("No alerts right now.") }
        } else {
            items(alerts.take(5)) {
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(Modifier.padding(12.dp)) {
                        Text(it.student.fullName, style = MaterialTheme.typography.titleSmall)
                        Text("Attendance: ${"%.1f".format(it.percentage)}%")
                    }
                }
            }
        }
    }
}

@Composable
private fun StatsCard(title: String, value: String) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(Modifier.padding(16.dp)) {
            Text(title, style = MaterialTheme.typography.titleMedium)
            Text(value, style = MaterialTheme.typography.headlineSmall)
        }
    }
}
