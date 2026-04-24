package com.college.attendance.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "students")
data class StudentEntity(
    @PrimaryKey val id: String,
    val rollNumber: String,
    val fullName: String,
    val year: Int,
    val branch: String
)

@Entity(tableName = "courses")
data class CourseEntity(
    @PrimaryKey val id: String,
    val code: String,
    val title: String,
    val section: String,
    val semester: String,
    val facultyId: String
)

@Entity(tableName = "attendance")
data class AttendanceEntity(
    @PrimaryKey val id: String,
    val courseId: String,
    val studentId: String,
    val date: String,
    val isPresent: Boolean,
    val markedBy: String,
    val remarks: String
)
