package com.zkrallah.z_students.domain.repositories

import com.zkrallah.z_students.domain.models.Request
import com.zkrallah.z_students.domain.response.ApiResponse

interface RequestsRepository {
    suspend fun getUserRequests(): ApiResponse<List<Request>?>?
}