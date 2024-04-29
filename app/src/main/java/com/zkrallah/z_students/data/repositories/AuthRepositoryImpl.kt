package com.zkrallah.z_students.data.repositories

import com.zkrallah.z_students.VERIFY_CODE
import com.zkrallah.z_students.data.dataStore.DataStore
import com.zkrallah.z_students.domain.dto.VerifyDto
import com.zkrallah.z_students.domain.repositories.AuthRepository
import com.zkrallah.z_students.domain.response.ApiResponse
import com.zkrallah.z_students.domain.response.MessageResponse
import com.zkrallah.zhttp.ZHttpClient

class AuthRepositoryImpl(
    private val zHttpClient: ZHttpClient,
    private val dataStore: DataStore
) : AuthRepository {
    override suspend fun verifyCode(email: String, code: Int): ApiResponse<MessageResponse?>? {
        val verifyDto = VerifyDto(email, code)
        val apiResponse =
            zHttpClient.post<ApiResponse<MessageResponse?>>(VERIFY_CODE, verifyDto, null, null)

        return apiResponse?.body
    }
}