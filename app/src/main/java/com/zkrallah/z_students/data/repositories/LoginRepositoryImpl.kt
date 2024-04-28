package com.zkrallah.z_students.data.repositories

import com.zkrallah.z_students.domain.dto.LoginDto
import com.zkrallah.z_students.domain.models.Token
import com.zkrallah.z_students.domain.repositories.LoginRepository
import com.zkrallah.zhttp.Response
import com.zkrallah.zhttp.ZHttpClient

class LoginRepositoryImpl(
    private val zHttpClient: ZHttpClient
) : LoginRepository {
    override suspend fun login(email: String, password: String): Response<Token>? {
        val loginDto = LoginDto(email, password)
        return zHttpClient.post<Token>("api/auth/login", loginDto, null, null)
    }
}