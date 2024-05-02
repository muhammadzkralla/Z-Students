package com.zkrallah.z_students.presentation.intro

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zkrallah.z_students.domain.repositories.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {
    private val _startingDestination: MutableStateFlow<String> = MutableStateFlow("OnBoarding")
    val startingDestination: StateFlow<String> = _startingDestination

    suspend fun getStartingDestination() {
        val isOnboardingDone = mainRepository.getOnBoardingDone()
        val isLoggedIn = mainRepository.getLoggedInDone()
        if (isOnboardingDone) {
            if (isLoggedIn) _startingDestination.emit("Browse")
            else _startingDestination.emit("Login")
        }
    }

    fun setOnBoardingStatus() {
        viewModelScope.launch {
            mainRepository.setOnBoardingDone()
        }
    }
}