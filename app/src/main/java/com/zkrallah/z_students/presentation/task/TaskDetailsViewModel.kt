package com.zkrallah.z_students.presentation.task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zkrallah.z_students.domain.models.Source
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
}