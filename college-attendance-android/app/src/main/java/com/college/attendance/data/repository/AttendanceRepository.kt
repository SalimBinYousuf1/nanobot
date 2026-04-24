package com.college.attendance.data.repository

import com.college.attendance.domain.model.AttendanceRecord
import com.college.attendance.domain.model.AttendanceSummary
import com.college.attendance.domain.model.Course
import com.college.attendance.domain.model.Student
import kotlinx.coroutines.flow.Flow

interface AttendanceRepository {
    val students: Flow<List<Student>>
    val courses: Flow<List<Course>>

    suspend fun refresh()
    suspend fun markAttendance(courseId: String, date: String, statuses: Map<String, Boolean>, markedBy: String)
    fun summaryForCourse(courseId: String): Flow<List<AttendanceSummary>>
    fun liveAlerts(threshold: Double = 75.0): Flow<List<AttendanceSummary>>
    suspend fun syncPending()

    val featureHighlights: List<String>
}

val defaultFeatureHighlights = listOf(
    "Role-based login (Admin, Faculty, Student, Parent)",
    "Class-wise attendance marking with bulk present/absent",
    "Low-attendance alerts with configurable threshold",
    "Offline-first local storage with background sync hook",
    "Analytics-ready summaries by course, student, semester",
    "Communication hooks for SMS/Email/Push notifications",
    "Export-ready architecture (CSV/PDF generation can plug in)",
    "Expandable to biometric/QR/RFID integrations"
)
