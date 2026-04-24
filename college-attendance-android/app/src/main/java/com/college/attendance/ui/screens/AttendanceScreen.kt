package com.college.attendance.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.college.attendance.viewmodel.AttendanceViewModel

@Composable
fun AttendanceScreen(vm: AttendanceViewModel) {
    val state by vm.uiState.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text("Attendance Marking", style = MaterialTheme.typography.headlineSmall)
            Text("Tap a quick action to mark attendance for all students.")
        }

        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = { vm.markToday(defaultPresent = true) }, modifier = Modifier.fillMaxWidth()) {
                    Text("Mark all present")
                }
                OutlinedButton(onClick = { vm.markToday(defaultPresent = false) }, modifier = Modifier.fillMaxWidth()) {
                    Text("Mark all absent")
                }
            }
        }

        items(state.students) { student ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(Modifier.padding(12.dp)) {
                    Text(student.fullName, style = MaterialTheme.typography.titleSmall)
                    Text("${student.rollNumber} • ${student.branch} Year ${student.year}")
                }
            }
        }
    }
}
