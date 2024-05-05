package com.zkrallah.z_students.data.repositories

import com.zkrallah.z_students.data.dataStore.DataStore
import com.zkrallah.z_students.domain.models.Class
import com.zkrallah.z_students.domain.models.Request
import com.zkrallah.z_students.domain.repositories.BrowseRepository
import com.zkrallah.z_students.domain.response.ApiResponse
import com.zkrallah.zhttp.Header
import com.zkrallah.zhttp.ZHttpClient

class BrowseRepositoryImpl(
    private val zHttpClient: ZHttpClient,
    private val dataStore: DataStore
) : BrowseRepository {
    override suspend fun getClasses(): ApiResponse<List<Class>?>? {
        val token = dataStore.getToken()
        val headers = listOf(
            Header("Authorization", "Bearer $token")
        )

        val apiResponse =
            zHttpClient.get<ApiResponse<List<Class>?>>("api/classes", null, headers)

        return apiResponse?.body
    }

    override suspend fun submitRequest(classId: Long): ApiResponse<Request?>? {
        val token = dataStore.getToken()
        val headers = listOf(
            Header("Authorization", "Bearer $token")
        )
        val userId = dataStore.getUserModel().id

        val apiResponse =
            zHttpClient.post<ApiResponse<Request?>>(
                "api/users/request/$userId/to/$classId",
                Unit,
                null,
                headers
            )

        return apiResponse?.body
    }
}