package com.zkrallah.z_students.domain.repositories

import com.zkrallah.z_students.domain.models.Class
import com.zkrallah.z_students.domain.models.Request
import com.zkrallah.z_students.domain.response.ApiResponse

interface BrowseRepository {
    suspend fun getClasses(): ApiResponse<List<Class>?>?

    suspend fun submitRequest(classId: Long): ApiResponse<Request?>?
}