package com.zkrallah.z_students.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zkrallah.z_students.domain.models.Token
import com.zkrallah.z_students.domain.repositories.LoginRepository
import com.zkrallah.z_students.domain.response.ApiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository
): ViewModel() {
    private val _loginStatus: MutableStateFlow<ApiResponse<Token?>?> = MutableStateFlow(null)
    val loginStatus: StateFlow<ApiResponse<Token?>?> = _loginStatus

    fun login(email: String, password: String) {
        viewModelScope.launch {
            val response = loginRepository.login(email, password)
            _loginStatus.emit(response)
        }
    }

}