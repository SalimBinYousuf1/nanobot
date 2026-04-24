package com.college.attendance.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AttendanceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertStudents(students: List<StudentEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertCourses(courses: List<CourseEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAttendance(records: List<AttendanceEntity>)

    @Query("SELECT * FROM students ORDER BY rollNumber ASC")
    fun students(): Flow<List<StudentEntity>>

    @Query("SELECT * FROM courses ORDER BY code ASC")
    fun courses(): Flow<List<CourseEntity>>

    @Query("SELECT * FROM attendance WHERE courseId = :courseId")
    fun attendanceForCourse(courseId: String): Flow<List<AttendanceEntity>>
}
