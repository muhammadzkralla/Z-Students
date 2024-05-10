package com.zkrallah.z_students.presentation.request

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zkrallah.z_students.domain.models.Request
import com.zkrallah.z_students.domain.repositories.RequestsRepository
import com.zkrallah.z_students.domain.response.ApiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RequestViewModel @Inject constructor(
    private val requestsRepository: RequestsRepository
) : ViewModel() {
    private val _getRequestsStatus: MutableStateFlow<ApiResponse<List<Request>?>?> = MutableStateFlow(null)
    val getRequestsStatus: StateFlow<ApiResponse<List<Request>?>?> = _getRequestsStatus

    init {
        viewModelScope.launch {
            _getRequestsStatus.emit(requestsRepository.getUserRequests())
        }
    }

    suspend fun refresh() {
        _getRequestsStatus.emit(requestsRepository.getUserRequests())
    }
}