package com.zkrallah.z_students.data.repositories

import com.zkrallah.z_students.data.dataStore.DataStore
import com.zkrallah.z_students.domain.repositories.MainRepository

class MainRepositoryImpl(
    private val dataStore: DataStore
) : MainRepository {
    override suspend fun getOnBoardingDone(): Boolean {
        return dataStore.getIsOnBoardingFinished()
    }

    override suspend fun setOnBoardingDone() {
        dataStore.setIsOnBoardingFinished(true)
    }

    override suspend fun getLoggedInDone(): Boolean {
        return dataStore.getIsLoggedIn()
    }

    override suspend fun setLoggedInDone() {
        dataStore.setIsLoggedIn(true)
    }
}