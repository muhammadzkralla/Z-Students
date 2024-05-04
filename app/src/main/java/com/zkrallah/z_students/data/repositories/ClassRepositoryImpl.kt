package com.zkrallah.z_students.data.repositories

import android.util.Log
import com.zkrallah.z_students.data.dataStore.DataStore
import com.zkrallah.z_students.domain.models.Class
import com.zkrallah.z_students.domain.repositories.ClassRepository
import com.zkrallah.z_students.domain.response.ApiResponse
import com.zkrallah.zhttp.Header
import com.zkrallah.zhttp.ZHttpClient

class ClassRepositoryImpl(
    private val zHttpClient: ZHttpClient,
    private val dataStore: DataStore
) : ClassRepository {
    override suspend fun getUserClasses(): ApiResponse<List<Class>?>? {
        val token = dataStore.getToken()
        val headers = listOf(
            Header("Authorization", "Bearer $token")
        )
        val userId = dataStore.getUserModel().id

        val apiResponse =
            zHttpClient.get<ApiResponse<List<Class>?>>("api/users/$userId/classes", null, headers)

        Log.d("UserClasses", "getUserClasses: $apiResponse")

        return apiResponse?.body
    }
}