package com.college.attendance.ui.screens

import androidx.compose.foundation.clickable
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
fun ClassesScreen(vm: AttendanceViewModel) {
    val state by vm.uiState.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item { Text("Courses", style = MaterialTheme.typography.headlineSmall) }

        items(state.courses) { course ->
            Card(modifier = Modifier.fillMaxWidth().clickable { vm.selectCourse(course.id) }) {
                Column(Modifier.padding(16.dp)) {
                    Text("${course.code} - ${course.title}", style = MaterialTheme.typography.titleMedium)
                    Text("Section ${course.section} • ${course.semester}")
                }
            }
        }
    }
}
