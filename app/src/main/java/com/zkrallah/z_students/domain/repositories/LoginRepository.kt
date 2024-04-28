package com.zkrallah.z_students.domain.repositories

import com.zkrallah.z_students.domain.models.Token
import com.zkrallah.zhttp.Response

interface LoginRepository {
    suspend fun login(email: String, password: String): Response<Token>?
}