package com.zkrallah.z_students.domain.repositories

import com.zkrallah.z_students.domain.models.User
import com.zkrallah.z_students.domain.response.ApiResponse

interface RegisterRepository {
    suspend fun registerStudent(email: String, password: String,
                                firstName: String, lastName: String): ApiResponse<User?>?

    suspend fun registerTeacher(email: String, password: String,
                                firstName: String, lastName: String): ApiResponse<User?>?

    suspend fun saveUser(user: User)
}