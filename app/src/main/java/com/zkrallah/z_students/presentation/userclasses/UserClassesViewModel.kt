package com.zkrallah.z_students.presentation.userclasses

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zkrallah.z_students.domain.models.Announcement
import com.zkrallah.z_students.domain.models.Class
import com.zkrallah.z_students.domain.models.Request
import com.zkrallah.z_students.domain.models.Task
import com.zkrallah.z_students.domain.models.User
import com.zkrallah.z_students.domain.repositories.ClassRepository
import com.zkrallah.z_students.domain.response.ApiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserClassesViewModel @Inject constructor(
    private val classRepository: ClassRepository
) : ViewModel() {
    private val _getUserClassesStatus: MutableStateFlow<ApiResponse<List<Class>?>?> =
        MutableStateFlow(null)
    val getUserClassesStatus: StateFlow<ApiResponse<List<Class>?>?> = _getUserClassesStatus

    private val _classMembersStatus: MutableStateFlow<ApiResponse<List<User>?>?> =
        MutableStateFlow(null)
    val classMembersStatus: StateFlow<ApiResponse<List<User>?>?> = _classMembersStatus

    private val _classAnnouncementsStatus: MutableStateFlow<ApiResponse<List<Announcement>?>?> =
        MutableStateFlow(null)
    val classAnnouncementsStatus: StateFlow<ApiResponse<List<Announcement>?>?> =
        _classAnnouncementsStatus

    private val _classTasksStatus: MutableStateFlow<ApiResponse<List<Task>?>?> =
        MutableStateFlow(null)
    val classTasksStatus: StateFlow<ApiResponse<List<Task>?>?> = _classTasksStatus

    private val _userRoleStatus: MutableStateFlow<String> = MutableStateFlow("STUDENT")
    val userRoleStatus: StateFlow<String> = _userRoleStatus

    private val _classRequestsStatus: MutableStateFlow<ApiResponse<List<Request>?>?> =
        MutableStateFlow(null)
    val classRequestsStatus: StateFlow<ApiResponse<List<Request>?>?> = _classRequestsStatus

    private val _requestResponseStatus: MutableStateFlow<ApiResponse<Request?>?> =
        MutableStateFlow(null)
    val requestResponseStatus: StateFlow<ApiResponse<Request?>?> = _requestResponseStatus

    private val _addTaskStatus: MutableStateFlow<ApiResponse<Task?>?> = MutableStateFlow(null)
    val addTaskStatus: StateFlow<ApiResponse<Task?>?> = _addTaskStatus

    private val _addAnnouncementStatus: MutableStateFlow<ApiResponse<Announcement?>?> =
        MutableStateFlow(null)
    val addAnnouncementStatus: StateFlow<ApiResponse<Announcement?>?> = _addAnnouncementStatus

    fun getUserClasses() {
        viewModelScope.launch {
            _getUserClassesStatus.emit(classRepository.getUserClasses())
        }
    }

    fun getClassMembers(classId: Long) {
        viewModelScope.launch {
            _classMembersStatus.emit(classRepository.getClassMembers(classId))
        }
    }

    fun getClassAnnouncements(classId: Long) {
        viewModelScope.launch {
            _classAnnouncementsStatus.emit(classRepository.getClassAnnouncements(classId))
        }
    }

    fun getClassTasks(classId: Long) {
        viewModelScope.launch {
            _classTasksStatus.emit(classRepository.getClassTasks(classId))
        }
    }

    fun getUserRole() {
        viewModelScope.launch {
            _userRoleStatus.emit(classRepository.getUserRole())
        }
    }

    fun getClassRequests(classId: Long) {
        viewModelScope.launch {
            _classRequestsStatus.emit(classRepository.getClassRequests(classId))
        }
    }

    fun approveRequest(requestId: Long) {
        viewModelScope.launch {
            _requestResponseStatus.emit(classRepository.approveRequest(requestId))
        }
    }

    fun declineRequest(requestId: Long) {
        viewModelScope.launch {
            _requestResponseStatus.emit(classRepository.declineRequest(requestId))
        }
    }

    fun addTask(classId: Long, title: String, desc: String, due: String) {
        viewModelScope.launch {
            _addTaskStatus.emit(classRepository.addTask(classId, title, desc, due))
        }
    }

    fun addAnnouncement(classId: Long, title: String, content: String) {
        viewModelScope.launch {
            _addAnnouncementStatus.emit(classRepository.addAnnouncement(classId, title, content))
        }
    }
}