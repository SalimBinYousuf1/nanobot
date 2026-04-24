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
fun ReportsScreen(vm: AttendanceViewModel) {
    val summary by vm.summary.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text("Reports & Analytics", style = MaterialTheme.typography.headlineSmall)
            Text("Student-wise percentage, defaulter insights and action-ready data.")
        }

        items(summary) { row ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(Modifier.padding(12.dp)) {
                    Text(row.student.fullName, style = MaterialTheme.typography.titleSmall)
                    Text("Present ${row.presentClasses}/${row.totalClasses}")
                    Text("${"%.1f".format(row.percentage)}% attendance")
                }
            }
        }
    }
}
