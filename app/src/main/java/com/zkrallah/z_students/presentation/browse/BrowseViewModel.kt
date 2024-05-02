package com.zkrallah.z_students.presentation.browse

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zkrallah.z_students.domain.models.Class
import com.zkrallah.z_students.domain.models.Request
import com.zkrallah.z_students.domain.repositories.BrowseRepository
import com.zkrallah.z_students.domain.response.ApiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BrowseViewModel @Inject constructor(
    private val browseRepository: BrowseRepository
) : ViewModel() {
    private val _getClassesStatus: MutableStateFlow<ApiResponse<List<Class>?>?> = MutableStateFlow(null)
    val getClassesStatus: StateFlow<ApiResponse<List<Class>?>?> = _getClassesStatus

    private val _submitRequestStatus: MutableStateFlow<ApiResponse<Request?>?> = MutableStateFlow(null)
    val submitRequestStatus: StateFlow<ApiResponse<Request?>?> = _submitRequestStatus

    fun getClasses() {
        viewModelScope.launch {
            _getClassesStatus.emit(browseRepository.getClasses())
        }
    }

    fun submitRequest(classId: Long) {
        viewModelScope.launch {
            _submitRequestStatus.emit(browseRepository.submitRequest(classId))
        }
    }
}