package com.zkrallah.z_students.data.repositories

import com.zkrallah.z_students.REGISTER_STUDENT_ENDPOINT
import com.zkrallah.z_students.REGISTER_TEACHER_ENDPOINT
import com.zkrallah.z_students.data.dataStore.DataStore
import com.zkrallah.z_students.domain.dto.RegisterDto
import com.zkrallah.z_students.domain.models.User
import com.zkrallah.z_students.domain.repositories.RegisterRepository
import com.zkrallah.z_students.domain.response.ApiResponse
import com.zkrallah.zhttp.ZHttpClient

class RegisterRepositoryImpl(
    private val zHttpClient: ZHttpClient,
    private val dataStore: DataStore
) : RegisterRepository {
    override suspend fun registerStudent(
        email: String,
        password: String,
        firstName: String,
        lastName: String
    ): ApiResponse<User?>? {
        val registerDto = RegisterDto(email, password, firstName, lastName)
        val apiResponse =
            zHttpClient.post<ApiResponse<User?>>(REGISTER_STUDENT_ENDPOINT, registerDto,
                null, null)

        return apiResponse?.body
    }

    override suspend fun registerTeacher(
        email: String,
        password: String,
        firstName: String,
        lastName: String
    ): ApiResponse<User?>? {
        val registerDto = RegisterDto(email, password, firstName, lastName)
        val apiResponse =
            zHttpClient.post<ApiResponse<User?>>(REGISTER_TEACHER_ENDPOINT, registerDto,
                null, null)

        return apiResponse?.body
    }

    override suspend fun saveUser(user: User) {
        dataStore.saveUserModel(user)
    }
}