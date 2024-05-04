package com.zkrallah.z_students.domain.repositories

import com.zkrallah.z_students.domain.models.Class
import com.zkrallah.z_students.domain.response.ApiResponse

interface ClassRepository {
    suspend fun getUserClasses(): ApiResponse<List<Class>?>?
}