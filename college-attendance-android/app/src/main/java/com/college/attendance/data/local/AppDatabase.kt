package com.college.attendance.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [StudentEntity::class, CourseEntity::class, AttendanceEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun attendanceDao(): AttendanceDao
}
