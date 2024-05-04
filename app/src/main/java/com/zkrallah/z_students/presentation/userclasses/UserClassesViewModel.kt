package com.zkrallah.z_students.presentation.userclasses

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zkrallah.z_students.domain.models.Class
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

    fun getUserClasses() {
        viewModelScope.launch {
            _getUserClassesStatus.emit(classRepository.getUserClasses())
        }
    }
}