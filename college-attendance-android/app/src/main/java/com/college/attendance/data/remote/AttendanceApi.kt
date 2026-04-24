package com.college.attendance.data.remote

import com.college.attendance.domain.model.AttendanceRecord
import com.college.attendance.domain.model.Course
import com.college.attendance.domain.model.Student
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AttendanceApi {
    @GET("students")
    suspend fun students(): List<Student>

    @GET("courses")
    suspend fun courses(): List<Course>

    @POST("attendance/bulk")
    suspend fun submitAttendance(@Body records: List<AttendanceRecord>)
}
