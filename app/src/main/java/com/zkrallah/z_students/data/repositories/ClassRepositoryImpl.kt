package com.zkrallah.z_students.data.repositories

import android.util.Log
import com.zkrallah.z_students.data.dataStore.DataStore
import com.zkrallah.z_students.domain.dto.AnnouncementDto
import com.zkrallah.z_students.domain.dto.TaskDto
import com.zkrallah.z_students.domain.models.Announcement
import com.zkrallah.z_students.domain.models.Class
import com.zkrallah.z_students.domain.models.Request
import com.zkrallah.z_students.domain.models.Task
import com.zkrallah.z_students.domain.models.User
import com.zkrallah.z_students.domain.repositories.ClassRepository
import com.zkrallah.z_students.domain.response.ApiResponse
import com.zkrallah.zhttp.Header
import com.zkrallah.zhttp.ZHttpClient

class ClassRepositoryImpl(
    private val zHttpClient: ZHttpClient,
    private val dataStore: DataStore
) : ClassRepository {
    override suspend fun getUserClasses(): ApiResponse<List<Class>?>? {
        val token = dataStore.getToken()
        val headers = listOf(
            Header("Authorization", "Bearer $token")
        )
        val userId = dataStore.getUserModel().id

        val apiResponse = zHttpClient.get<ApiResponse<List<Class>?>>(
            "api/users/$userId/classes", headers = headers
        )

        Log.d("UserClasses", "getUserClasses: $apiResponse")

        return apiResponse?.body
    }

    override suspend fun getClassMembers(classId: Long): ApiResponse<List<User>?>? {
        val token = dataStore.getToken()
        val headers = listOf(
            Header("Authorization", "Bearer $token")
        )

        val apiResponse = zHttpClient.get<ApiResponse<List<User>?>>(
            "api/classes/$classId/users", headers = headers
        )

        return apiResponse?.body
    }

    override suspend fun getClassAnnouncements(classId: Long): ApiResponse<List<Announcement>?>? {
        val token = dataStore.getToken()
        val headers = listOf(
            Header("Authorization", "Bearer $token")
        )

        val apiResponse = zHttpClient.get<ApiResponse<List<Announcement>?>>(
            "api/classes/$classId/announcements", headers = headers
        )

        return apiResponse?.body
    }

    override suspend fun getClassTasks(classId: Long): ApiResponse<List<Task>?>? {
        val token = dataStore.getToken()
        val headers = listOf(
            Header("Authorization", "Bearer $token")
        )

        val apiResponse = zHttpClient.get<ApiResponse<List<Task>?>>(
            "api/classes/$classId/tasks", headers = headers
        )

        Log.d("TasksScreen", "getClassTasks: $apiResponse")

        return apiResponse?.body
    }

    override suspend fun getUserRole(): String {
        return dataStore.getUserModel().authorities?.get(0)?.name ?: "STUDENT"
    }

    override suspend fun getClassRequests(classId: Long): ApiResponse<List<Request>?>? {
        val token = dataStore.getToken()
        val headers = listOf(
            Header("Authorization", "Bearer $token")
        )

        val apiResponse = zHttpClient.get<ApiResponse<List<Request>?>>(
            "api/classes/$classId/requests", headers = headers
        )

        return apiResponse?.body
    }

    override suspend fun approveRequest(requestId: Long): ApiResponse<Request?>? {
        val token = dataStore.getToken()
        val headers = listOf(
            Header("Authorization", "Bearer $token")
        )

        val apiResponse = zHttpClient.put<ApiResponse<Request?>>(
            "api/admin/approve-request/$requestId", body = Unit, headers = headers
        )

        return apiResponse?.body
    }

    override suspend fun declineRequest(requestId: Long): ApiResponse<Request?>? {
        val token = dataStore.getToken()
        val headers = listOf(
            Header("Authorization", "Bearer $token")
        )

        val apiResponse = zHttpClient.put<ApiResponse<Request?>>(
            "api/admin/decline-request/$requestId", body = Unit, headers = headers
        )

        return apiResponse?.body
    }

    override suspend fun addTask(
        classId: Long, title: String, desc: String, due: String
    ): ApiResponse<Task?>? {
        val token = dataStore.getToken()
        val headers = listOf(
            Header("Authorization", "Bearer $token")
        )

        val taskDto = TaskDto(
            title, desc, due
        )

        val apiResponse = zHttpClient.post<ApiResponse<Task?>>(
            "api/teachers/class/$classId/create-task", body = taskDto, headers = headers
        )

        return apiResponse?.body
    }

    override suspend fun addAnnouncement(
        classId: Long, title: String, content: String
    ): ApiResponse<Announcement?>? {
        val token = dataStore.getToken()
        val headers = listOf(
            Header("Authorization", "Bearer $token")
        )

        val announcementDto = AnnouncementDto(title, content)

        val apiResponse = zHttpClient.post<ApiResponse<Announcement?>>(
            "api/teachers/create-announcement/$classId", body = announcementDto, headers = headers
        )

        return apiResponse?.body
    }
}