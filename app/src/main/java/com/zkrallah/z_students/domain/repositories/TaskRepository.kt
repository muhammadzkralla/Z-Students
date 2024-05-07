package com.zkrallah.z_students.domain.repositories

import com.zkrallah.z_students.domain.models.Source
import com.zkrallah.z_students.domain.models.Task
import com.zkrallah.z_students.domain.response.ApiResponse

interface TaskRepository {
    suspend fun getUserRole(): String

    suspend fun getTask(taskId: Long): ApiResponse<Task?>?

    suspend fun addSource(taskId: Long, source: String): ApiResponse<Source?>?
}