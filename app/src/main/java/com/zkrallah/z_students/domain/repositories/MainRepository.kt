package com.zkrallah.z_students.domain.repositories

interface MainRepository {
    suspend fun getOnBoardingDone(): Boolean

    suspend fun setOnBoardingDone()

    suspend fun getLoggedInDone(): Boolean

    suspend fun setLoggedInDone()
}