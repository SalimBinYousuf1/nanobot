package com.college.attendance.data.repository

import com.college.attendance.domain.model.AttendanceRecord
import com.college.attendance.domain.model.AttendanceSummary
import com.college.attendance.domain.model.Course
import com.college.attendance.domain.model.Student
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import java.util.UUID

class InMemoryAttendanceRepository : AttendanceRepository {
    private val studentsFlow = MutableStateFlow(seedStudents)
    private val coursesFlow = MutableStateFlow(seedCourses)
    private val attendanceFlow = MutableStateFlow(seedAttendance)

    override val students: Flow<List<Student>> = studentsFlow
    override val courses: Flow<List<Course>> = coursesFlow
    override val featureHighlights: List<String> = defaultFeatureHighlights

    override suspend fun refresh() = Unit

    override suspend fun markAttendance(courseId: String, date: String, statuses: Map<String, Boolean>, markedBy: String) {
        val rows = statuses.map { (studentId, isPresent) ->
            AttendanceRecord(
                id = UUID.randomUUID().toString(),
                courseId = courseId,
                studentId = studentId,
                date = LocalDate.parse(date),
                isPresent = isPresent,
                markedBy = markedBy,
                remarks = if (isPresent) "" else "Absent"
            )
        }
        attendanceFlow.value = attendanceFlow.value + rows
    }

    override fun summaryForCourse(courseId: String): Flow<List<AttendanceSummary>> =
        combine(studentsFlow, attendanceFlow) { students, rows ->
            students.map { student ->
                val studentRows = rows.filter { it.courseId == courseId && it.studentId == student.id }
                AttendanceSummary(
                    student = student,
                    presentClasses = studentRows.count { it.isPresent },
                    totalClasses = studentRows.size
                )
            }
        }

    override fun liveAlerts(threshold: Double): Flow<List<AttendanceSummary>> =
        combine(coursesFlow, attendanceFlow, studentsFlow) { courses, rows, students ->
            courses.flatMap { course ->
                students.map { student ->
                    val studentRows = rows.filter { it.courseId == course.id && it.studentId == student.id }
                    AttendanceSummary(
                        student = student,
                        presentClasses = studentRows.count { it.isPresent },
                        totalClasses = studentRows.size
                    )
                }
            }.filter { it.totalClasses > 0 && it.percentage < threshold }
        }

    override suspend fun syncPending() = Unit
}

private val seedStudents = listOf(
    Student("S1", "23CSE001", "Aarav Sharma", 2, "CSE"),
    Student("S2", "23CSE002", "Isha Patel", 2, "CSE"),
    Student("S3", "23ECE017", "Kabir Verma", 2, "ECE")
)

private val seedCourses = listOf(
    Course("C1", "CSE-221", "Data Structures", "A", "Monsoon 2026", "F001"),
    Course("C2", "CSE-241", "Database Systems", "A", "Monsoon 2026", "F001")
)

private val seedAttendance = listOf(
    AttendanceRecord("A1", "C1", "S1", LocalDate.parse("2026-04-18"), true, "F001"),
    AttendanceRecord("A2", "C1", "S2", LocalDate.parse("2026-04-18"), false, "F001"),
    AttendanceRecord("A3", "C1", "S3", LocalDate.parse("2026-04-18"), true, "F001"),
    AttendanceRecord("A4", "C2", "S1", LocalDate.parse("2026-04-19"), true, "F001"),
    AttendanceRecord("A5", "C2", "S2", LocalDate.parse("2026-04-19"), true, "F001"),
    AttendanceRecord("A6", "C2", "S3", LocalDate.parse("2026-04-19"), false, "F001")
)
