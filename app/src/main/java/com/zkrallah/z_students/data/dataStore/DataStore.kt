package com.zkrallah.z_students.data.dataStore

interface DataStore {
    suspend fun getIsOnBoardingFinished():Boolean

    suspend fun setIsOnBoardingFinished(isOnBoardingFinished:Boolean)

    suspend fun getIsLoggedIn(): Boolean

    suspend fun setIsLoggedIn(isLogged: Boolean)

    suspend fun insertToken(token: String)

    suspend fun getToken(): String

    suspend fun logOut()
}