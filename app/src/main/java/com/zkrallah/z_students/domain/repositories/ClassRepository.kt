package com.zkrallah.z_students.domain.repositories

import com.zkrallah.z_students.domain.models.Announcement
import com.zkrallah.z_students.domain.models.Class
import com.zkrallah.z_students.domain.models.User
import com.zkrallah.z_students.domain.response.ApiResponse

interface ClassRepository {
    suspend fun getUserClasses(): ApiResponse<List<Class>?>?

    suspend fun getClassMembers(classId: Long): ApiResponse<List<User>?>?

    suspend fun getClassAnnouncements(classId: Long): ApiResponse<List<Announcement>?>?
}