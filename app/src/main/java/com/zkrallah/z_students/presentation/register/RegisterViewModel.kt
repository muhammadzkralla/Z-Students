package com.zkrallah.z_students.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zkrallah.z_students.domain.models.User
import com.zkrallah.z_students.domain.repositories.AuthRepository
import com.zkrallah.z_students.domain.response.ApiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _registerStatus: MutableStateFlow<ApiResponse<User?>?> = MutableStateFlow(null)
    val registerStatus: StateFlow<ApiResponse<User?>?> = _registerStatus

    fun registerStudent(email: String, password: String, firstName: String, lastName: String) {
        viewModelScope.launch {
            val response = authRepository.registerStudent(email, password, firstName, lastName)
            _registerStatus.emit(response)
        }
    }

    fun registerTeacher(email: String, password: String, firstName: String, lastName: String) {
        viewModelScope.launch {
            val response = authRepository.registerTeacher(email, password, firstName, lastName)
            _registerStatus.emit(response)
        }
    }

    fun saveUser(user: User) {
        viewModelScope.launch {
            authRepository.saveUser(user)
        }
    }
}