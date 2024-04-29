package com.zkrallah.z_students.presentation.verification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zkrallah.z_students.domain.models.User
import com.zkrallah.z_students.domain.repositories.AuthRepository
import com.zkrallah.z_students.domain.response.ApiResponse
import com.zkrallah.z_students.domain.response.MessageResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VerificationViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _verificationStatus: MutableStateFlow<ApiResponse<MessageResponse?>?> = MutableStateFlow(null)
    val verificationStatus: StateFlow<ApiResponse<MessageResponse?>?> = _verificationStatus


    fun verifyCode(email: String, code: Int) {
        viewModelScope.launch {
            _verificationStatus.emit(authRepository.verifyCode(email, code))
        }
    }

    fun getUserEmail() {
        viewModelScope.launch {

        }
    }
}