package com.zkrallah.z_students.data.repositories

import com.zkrallah.z_students.data.dataStore.DataStore
import com.zkrallah.z_students.domain.models.Request
import com.zkrallah.z_students.domain.repositories.RequestsRepository
import com.zkrallah.z_students.domain.response.ApiResponse
import com.zkrallah.zhttp.model.Header
import com.zkrallah.zhttp.client.ZHttpClient

class RequestRepositoryImpl(
    private val zHttpClient: ZHttpClient,
    private val dataStore: DataStore
) : RequestsRepository {
    override suspend fun getUserRequests(): ApiResponse<List<Request>?>? {
        val token = dataStore.getToken()
        val headers = listOf(
            Header("Authorization", "Bearer $token")
        )
        val userId = dataStore.getUserModel().id

        val apiResponse = zHttpClient.get<ApiResponse<List<Request>?>>(
            "api/users/$userId/requests", headers = headers
        )

        return apiResponse?.body
    }
}