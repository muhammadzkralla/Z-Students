package com.zkrallah.z_students.presentation.reset

import androidx.lifecycle.ViewModel
import com.zkrallah.z_students.domain.repositories.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

}