package com.zkrallah.z_students.data.repositories

import com.zkrallah.z_students.data.dataStore.DataStore
import com.zkrallah.z_students.domain.dto.UpdateUserDto
import com.zkrallah.z_students.domain.models.User
import com.zkrallah.z_students.domain.repositories.UserRepository
import com.zkrallah.z_students.domain.response.ApiResponse
import com.zkrallah.z_students.domain.response.MessageResponse
import com.zkrallah.zhttp.model.Header
import com.zkrallah.zhttp.model.MultipartBody
import com.zkrallah.zhttp.client.ZHttpClient

class UserRepositoryImpl(
    private val zHttpClient: ZHttpClient,
    private val dataStore: DataStore
) : UserRepository {
    override suspend fun getCurrentUser(): ApiResponse<User?>? {
        val token = dataStore.getToken()
        val headers = listOf(
            Header("Authorization", "Bearer $token")
        )
        val userId = dataStore.getUserModel().id

        val apiResponse =
            zHttpClient.get<ApiResponse<User?>>("api/users/$userId", headers = headers)

        apiResponse?.body?.let { response ->
            if (response.success) dataStore.saveUserModel(response.data)
        }

        return apiResponse?.body
    }

    override suspend fun updateUser(
        firstName: String, lastName: String, dob: String
    ): ApiResponse<User?>? {
        val token = dataStore.getToken()
        val headers = listOf(
            Header("Authorization", "Bearer $token")
        )
        val userId = dataStore.getUserModel().id

        val updateUserDto = UpdateUserDto(firstName, lastName, dob)

        val apiResponse = zHttpClient.put<ApiResponse<User?>>(
            "api/users/update-user/$userId", body = updateUserDto, headers = headers
        )

        apiResponse?.body?.let { response ->
            if (response.success) dataStore.saveUserModel(response.data)
        }

        return apiResponse?.body
    }

    override suspend fun uploadProfilePicture(filePath: String): ApiResponse<MessageResponse?>? {
        val token = dataStore.getToken()
        val headers = listOf(
            Header("Authorization", "Bearer $token")
        )
        val userId = dataStore.getUserModel().id

        val imageMultipartBody = MultipartBody(
            name = "file", filePath = filePath, contentType = "image/*"
        )
        val parts = listOf(imageMultipartBody)

        val apiResponse = zHttpClient.multiPart<ApiResponse<MessageResponse?>>(
            "api/users/${userId}/upload-image", parts = parts, headers = headers
        )

        return apiResponse?.body
    }

    override suspend fun logOut() {
        dataStore.logOut()
    }
}