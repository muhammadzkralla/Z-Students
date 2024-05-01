package com.zkrallah.z_students.domain.repositories

import com.zkrallah.z_students.domain.models.User
import com.zkrallah.z_students.domain.response.ApiResponse
import com.zkrallah.z_students.domain.response.MessageResponse
import com.zkrallah.zhttp.MultipartBody

interface UserRepository {
    suspend fun getCurrentUser(): ApiResponse<User?>?

    suspend fun updateUser(firstName: String, lastName: String, dob: String): ApiResponse<User?>?

    suspend fun uploadProfilePicture(filePath: String): ApiResponse<MessageResponse?>?
}