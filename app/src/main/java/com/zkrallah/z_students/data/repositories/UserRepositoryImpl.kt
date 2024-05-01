package com.zkrallah.z_students.data.repositories

import com.zkrallah.z_students.GET_USER
import com.zkrallah.z_students.UPDATE_USER
import com.zkrallah.z_students.UPLOAD_PHOTO
import com.zkrallah.z_students.data.dataStore.DataStore
import com.zkrallah.z_students.domain.dto.UpdateUserDto
import com.zkrallah.z_students.domain.models.User
import com.zkrallah.z_students.domain.repositories.UserRepository
import com.zkrallah.z_students.domain.response.ApiResponse
import com.zkrallah.z_students.domain.response.MessageResponse
import com.zkrallah.zhttp.Header
import com.zkrallah.zhttp.MultipartBody
import com.zkrallah.zhttp.ZHttpClient

class UserRepositoryImpl(
    private val zHttpClient: ZHttpClient,
    private val dataStore: DataStore
) : UserRepository {
    override suspend fun getCurrentUser(): ApiResponse<User?>? {
        val token = dataStore.getToken()
        val authHeader = Header("Authorization", "Bearer $token")
        val userId = dataStore.getUserModel().id

        val apiResponse =
            zHttpClient.get<ApiResponse<User?>>(GET_USER(userId!!), null, listOf(authHeader))

        apiResponse?.body?.let { response ->
            if (response.success) dataStore.saveUserModel(response.data)
        }

        return apiResponse?.body
    }

    override suspend fun updateUser(
        firstName: String,
        lastName: String,
        dob: String
    ): ApiResponse<User?>? {
        val token = dataStore.getToken()
        val authHeader = Header("Authorization", "Bearer $token")
        val userId = dataStore.getUserModel().id

        val updateUserDto = UpdateUserDto(firstName, lastName, dob)

        val apiResponse =
            zHttpClient.put<ApiResponse<User?>>(
                UPDATE_USER(userId!!),
                updateUserDto,
                null,
                listOf(authHeader)
            )

        apiResponse?.body?.let { response ->
            if (response.success) dataStore.saveUserModel(response.data)
        }

        return apiResponse?.body
    }

    override suspend fun uploadProfilePicture(filePath: String): ApiResponse<MessageResponse?>? {
        val token = dataStore.getToken()
        val authHeader = Header("Authorization", "Bearer $token")
        val userId = dataStore.getUserModel().id

        val imageMultipartBody = MultipartBody(
            fileName = "image",
            filePath = filePath,
            contentType = "image/*"
        )
        val parts = listOf(imageMultipartBody)

        val apiResponse =
            zHttpClient.multiPart<ApiResponse<MessageResponse?>>(
                UPLOAD_PHOTO(userId!!),
                parts,
                null,
                listOf(authHeader)
            )

        apiResponse?.body?.let { response ->
            if (response.success) {
                val savedUser = dataStore.getUserModel()
                savedUser.imageUrl = response.data?.message
                dataStore.saveUserModel(savedUser)
            }
        }

        return apiResponse?.body
    }
}