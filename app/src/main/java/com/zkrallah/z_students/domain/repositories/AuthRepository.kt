package com.zkrallah.z_students.domain.repositories

import com.zkrallah.z_students.domain.models.Token
import com.zkrallah.z_students.domain.models.User
import com.zkrallah.z_students.domain.response.ApiResponse
import com.zkrallah.z_students.domain.response.MessageResponse

interface AuthRepository {
    suspend fun registerStudent(email: String, password: String,
                                firstName: String, lastName: String): ApiResponse<User?>?

    suspend fun registerTeacher(email: String, password: String,
                                firstName: String, lastName: String): ApiResponse<User?>?

    suspend fun saveUser(user: User)

    suspend fun login(email: String, password: String): ApiResponse<Token?>?

    suspend fun setLoggedInDone()

    suspend fun saveUserData(data: Token)

    suspend fun verifyCode(email: String, code: Int): ApiResponse<MessageResponse?>?
}