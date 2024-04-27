package com.zkrallah.z_students.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zkrallah.z_students.data.dataStore.DataStore
import com.zkrallah.z_students.domain.repositories.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    private val _startingDestination: MutableStateFlow<String> = MutableStateFlow("OnBoarding")
    val startingDestination: StateFlow<String> = _startingDestination

    suspend fun getStartingDestination() {
        val isOnboardingDone = mainRepository.getOnBoardingDone()
        val isLoggedIn = mainRepository.getLoggedInDone()
        if (isOnboardingDone) {
            if (isLoggedIn) _startingDestination.emit("Home")
            else _startingDestination.emit("Login")
        }
    }

    fun setOnBoardingStatus() {
        viewModelScope.launch {
            mainRepository.setOnBoardingDone()
        }
    }

    fun setLoggedInStatus() {
        viewModelScope.launch {
            mainRepository.setLoggedInDone()
        }
    }

}