package com.zkrallah.z_students.domain.repositories

import com.zkrallah.z_students.domain.response.ApiResponse
import com.zkrallah.z_students.domain.response.MessageResponse

interface AuthRepository {
    suspend fun verifyCode(email: String, code: Int): ApiResponse<MessageResponse?>?
}