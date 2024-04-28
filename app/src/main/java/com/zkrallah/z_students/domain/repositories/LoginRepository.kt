package com.zkrallah.z_students.domain.repositories

import com.zkrallah.z_students.domain.models.Token
import com.zkrallah.z_students.domain.response.ApiResponse

interface LoginRepository {
    suspend fun login(email: String, password: String): ApiResponse<Token?>?
}