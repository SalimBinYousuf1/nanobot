package com.college.attendance.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.college.attendance.data.repository.AttendanceRepository
import com.college.attendance.data.repository.InMemoryAttendanceRepository
import com.college.attendance.domain.model.AttendanceSummary
import com.college.attendance.domain.model.Course
import com.college.attendance.domain.model.Student
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate

data class AttendanceUiState(
    val selectedCourseId: String? = null,
    val students: List<Student> = emptyList(),
    val courses: List<Course> = emptyList(),
    val featureHighlights: List<String> = emptyList()
)

class AttendanceViewModel(
    private val repository: AttendanceRepository = InMemoryAttendanceRepository()
) : ViewModel() {
    private val selectedCourseId = MutableStateFlow<String?>(null)

    val uiState: StateFlow<AttendanceUiState> = combine(
        repository.students,
        repository.courses,
        selectedCourseId
    ) { students, courses, selected ->
        AttendanceUiState(
            selectedCourseId = selected ?: courses.firstOrNull()?.id,
            students = students,
            courses = courses,
            featureHighlights = repository.featureHighlights
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), AttendanceUiState())

    val summary: StateFlow<List<AttendanceSummary>> = combine(selectedCourseId, repository.courses) { selected, courses ->
        selected ?: courses.firstOrNull()?.id
    }.flatMapLatest { courseId ->
        repository.summaryForCourse(courseId ?: "")
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    val alerts: StateFlow<List<AttendanceSummary>> = repository.liveAlerts()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    init {
        viewModelScope.launch { repository.refresh() }
    }

    fun selectCourse(courseId: String) {
        selectedCourseId.value = courseId
    }

    fun markToday(defaultPresent: Boolean = true) {
        val state = uiState.value
        val courseId = state.selectedCourseId ?: return
        viewModelScope.launch {
            repository.markAttendance(
                courseId = courseId,
                date = LocalDate.now().toString(),
                statuses = state.students.associate { it.id to defaultPresent },
                markedBy = "F001"
            )
        }
    }
}
