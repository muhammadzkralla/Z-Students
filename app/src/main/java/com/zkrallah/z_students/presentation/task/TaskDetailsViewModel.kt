package com.zkrallah.z_students.presentation.task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zkrallah.z_students.domain.models.Source
import com.zkrallah.z_students.domain.models.Submission
import com.zkrallah.z_students.domain.models.Task
import com.zkrallah.z_students.domain.repositories.TaskRepository
import com.zkrallah.z_students.domain.response.ApiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskDetailsViewModel @Inject constructor(
    private val taskRepository: TaskRepository
) : ViewModel() {
    private val _userRoleStatus: MutableStateFlow<String> = MutableStateFlow("STUDENT")
    val userRoleStatus: StateFlow<String> = _userRoleStatus

    private val _getTaskStatus: MutableStateFlow<ApiResponse<Task?>?> = MutableStateFlow(null)
    val getTaskStatus: StateFlow<ApiResponse<Task?>?> = _getTaskStatus

    private val _addSourceStatus: MutableStateFlow<ApiResponse<Source?>?> = MutableStateFlow(null)
    val addSourceStatus: StateFlow<ApiResponse<Source?>?> = _addSourceStatus

    private val _getTaskSubmissionsStatus: MutableStateFlow<ApiResponse<List<Submission>?>?> =
        MutableStateFlow(null)
    val getTaskSubmissionsStatus: StateFlow<ApiResponse<List<Submission>?>?> =
        _getTaskSubmissionsStatus

    private val _updateSubmissionStatus: MutableStateFlow<ApiResponse<Submission?>?> =
        MutableStateFlow(null)
    val updateSubmissionStatus: StateFlow<ApiResponse<Submission?>?> = _updateSubmissionStatus

    private val _addSubmissionStatus: MutableStateFlow<ApiResponse<Submission?>?> = MutableStateFlow(null)
    val addSubmissionStatus: StateFlow<ApiResponse<Submission?>?> = _addSubmissionStatus

    private val _getUserTaskSubmissionsStatus: MutableStateFlow<ApiResponse<List<Submission>?>?> =
        MutableStateFlow(null)
    val getUserTaskSubmissionsStatus: StateFlow<ApiResponse<List<Submission>?>?> =
        _getUserTaskSubmissionsStatus

    fun getUserRole() {
        viewModelScope.launch {
            _userRoleStatus.emit(taskRepository.getUserRole())
        }
    }

    fun getTask(taskId: Long) {
        viewModelScope.launch {
            _getTaskStatus.emit(taskRepository.getTask(taskId))
        }
    }

    fun addSource(taskId: Long, source: String) {
        viewModelScope.launch {
            _addSourceStatus.emit(taskRepository.addSource(taskId, source))
        }
    }

    fun getTaskSubmissions(taskId: Long) {
        viewModelScope.launch {
            _getTaskSubmissionsStatus.emit(taskRepository.getTaskSubmissions(taskId))
        }
    }

    fun updateSubmission(submissionId: Long, link: String, grade: Int, additional: String) {
        viewModelScope.launch {
            _updateSubmissionStatus.emit(
                taskRepository.updateSubmission(
                    submissionId,
                    link,
                    grade,
                    additional
                )
            )
        }
    }

    fun addSubmission(taskId: Long, link: String, additional: String) {
        viewModelScope.launch {
            _addSubmissionStatus.emit(taskRepository.addSubmission(taskId, link, additional))
        }
    }

    fun getUserTaskSubmissions(taskId: Long) {
        viewModelScope.launch {
            _getUserTaskSubmissionsStatus.emit(taskRepository.getUserTaskSubmissions(taskId))
        }
    }
}