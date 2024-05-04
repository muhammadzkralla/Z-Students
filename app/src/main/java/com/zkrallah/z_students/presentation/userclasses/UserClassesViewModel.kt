package com.zkrallah.z_students.presentation.userclasses

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zkrallah.z_students.domain.models.Announcement
import com.zkrallah.z_students.domain.models.Class
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
    private val _getUserClassesStatus: MutableStateFlow<ApiResponse<List<Class>?>?> = MutableStateFlow(null)
    val getUserClassesStatus: StateFlow<ApiResponse<List<Class>?>?> = _getUserClassesStatus

    private val _classMembersStatus: MutableStateFlow<ApiResponse<List<User>?>?> = MutableStateFlow(null)
    val classMembersStatus: StateFlow<ApiResponse<List<User>?>?> = _classMembersStatus

    private val _classAnnouncementsStatus: MutableStateFlow<ApiResponse<List<Announcement>?>?> = MutableStateFlow(null)
    val classAnnouncementsStatus: StateFlow<ApiResponse<List<Announcement>?>?> = _classAnnouncementsStatus

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
}