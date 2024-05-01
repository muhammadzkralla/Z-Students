package com.zkrallah.z_students.presentation.reset

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zkrallah.z_students.domain.repositories.AuthRepository
import com.zkrallah.z_students.domain.response.ApiResponse
import com.zkrallah.z_students.domain.response.MessageResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _resendStatus: MutableStateFlow<ApiResponse<MessageResponse?>?> =
        MutableStateFlow(null)
    val resendStatus: StateFlow<ApiResponse<MessageResponse?>?> = _resendStatus
    private val _resetStatus: MutableStateFlow<ApiResponse<MessageResponse?>?> =
        MutableStateFlow(null)
    val resetStatus: StateFlow<ApiResponse<MessageResponse?>?> = _resetStatus

    fun resendCode(email: String) {
        viewModelScope.launch {
            _resendStatus.emit(authRepository.resendCode(email))
        }
    }

    fun resetPassword(email: String, password: String, code: Int) {
        viewModelScope.launch {
            _resetStatus.emit(authRepository.resetPassword(email, password, code))
        }
    }
}