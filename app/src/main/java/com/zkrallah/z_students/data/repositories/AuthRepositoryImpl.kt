package com.zkrallah.z_students.data.repositories

import com.zkrallah.z_students.LOGIN_ENDPOINT
import com.zkrallah.z_students.REGISTER_STUDENT_ENDPOINT
import com.zkrallah.z_students.REGISTER_TEACHER_ENDPOINT
import com.zkrallah.z_students.VERIFY_CODE
import com.zkrallah.z_students.data.dataStore.DataStore
import com.zkrallah.z_students.domain.dto.LoginDto
import com.zkrallah.z_students.domain.dto.RegisterDto
import com.zkrallah.z_students.domain.dto.VerifyDto
import com.zkrallah.z_students.domain.models.Token
import com.zkrallah.z_students.domain.models.User
import com.zkrallah.z_students.domain.repositories.AuthRepository
import com.zkrallah.z_students.domain.response.ApiResponse
import com.zkrallah.z_students.domain.response.MessageResponse
import com.zkrallah.zhttp.ZHttpClient

class AuthRepositoryImpl(
    private val zHttpClient: ZHttpClient,
    private val dataStore: DataStore
) : AuthRepository {

    override suspend fun registerStudent(
        email: String,
        password: String,
        firstName: String,
        lastName: String
    ): ApiResponse<User?>? {
        val registerDto = RegisterDto(email, password, firstName, lastName)
        val apiResponse =
            zHttpClient.post<ApiResponse<User?>>(
                REGISTER_STUDENT_ENDPOINT, registerDto,
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
            zHttpClient.post<ApiResponse<User?>>(
                REGISTER_TEACHER_ENDPOINT, registerDto,
                null, null)

        return apiResponse?.body
    }

    override suspend fun saveUser(user: User) {
        dataStore.saveUserModel(user)
    }

    override suspend fun login(email: String, password: String): ApiResponse<Token?>? {
        val loginDto = LoginDto(email, password)
        val apiResponse = zHttpClient.post<ApiResponse<Token?>>(LOGIN_ENDPOINT, loginDto, null, null)

        return apiResponse?.body
    }

    override suspend fun setLoggedInDone() {
        dataStore.setIsLoggedIn(true)
    }

    override suspend fun saveUserData(data: Token) {
        dataStore.insertToken(data)
    }
    override suspend fun verifyCode(email: String, code: Int): ApiResponse<MessageResponse?>? {
        val verifyDto = VerifyDto(email, code)
        val apiResponse =
            zHttpClient.post<ApiResponse<MessageResponse?>>(VERIFY_CODE, verifyDto, null, null)

        return apiResponse?.body
    }
}