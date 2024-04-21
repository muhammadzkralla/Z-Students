package com.zkrallah.z_students.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zkrallah.z_students.data.dataStore.DataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dataStore: DataStore
) : ViewModel() {

    private val _onBoarding: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val onBoarding: StateFlow<Boolean> = _onBoarding

    suspend fun isOnBoardingDone() {
        _onBoarding.emit(dataStore.getIsOnBoardingFinished())
    }

    fun setOnBoardingDone() {
        viewModelScope.launch {
            dataStore.setIsOnBoardingFinished(true)
        }
    }

}