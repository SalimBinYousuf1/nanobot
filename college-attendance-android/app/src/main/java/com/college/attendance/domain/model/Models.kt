package com.college.attendance.domain.model

import java.time.LocalDate

enum class UserRole { ADMIN, FACULTY, STUDENT, PARENT }

data class User(
    val id: String,
    val name: String,
    val email: String,
    val role: UserRole,
    val department: String
)

data class Course(
    val id: String,
    val code: String,
    val title: String,
    val section: String,
    val semester: String,
    val facultyId: String
)

data class Student(
    val id: String,
    val rollNumber: String,
    val fullName: String,
    val year: Int,
    val branch: String
)

data class AttendanceRecord(
    val id: String,
    val courseId: String,
    val studentId: String,
    val date: LocalDate,
    val isPresent: Boolean,
    val markedBy: String,
    val remarks: String = ""
)

data class AttendanceSummary(
    val student: Student,
    val presentClasses: Int,
    val totalClasses: Int
) {
    val percentage: Double
        get() = if (totalClasses == 0) 0.0 else (presentClasses.toDouble() / totalClasses) * 100
}
