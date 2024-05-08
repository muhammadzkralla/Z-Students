package com.zkrallah.z_students.data.repositories

import com.zkrallah.z_students.data.dataStore.DataStore
import com.zkrallah.z_students.domain.dto.SourceDto
import com.zkrallah.z_students.domain.dto.SubmissionDto
import com.zkrallah.z_students.domain.models.Source
import com.zkrallah.z_students.domain.models.Submission
import com.zkrallah.z_students.domain.models.Task
import com.zkrallah.z_students.domain.repositories.TaskRepository
import com.zkrallah.z_students.domain.response.ApiResponse
import com.zkrallah.zhttp.Header
import com.zkrallah.zhttp.ZHttpClient

class TaskRepositoryImpl(
    private val zHttpClient: ZHttpClient,
    private val dataStore: DataStore
) : TaskRepository {
    override suspend fun getUserRole(): String {
        return dataStore.getUserModel().authorities?.get(0)?.name ?: "STUDENT"
    }

    override suspend fun getTask(taskId: Long): ApiResponse<Task?>? {
        val token = dataStore.getToken()
        val headers = listOf(
            Header("Authorization", "Bearer $token")
        )

        val apiResponse = zHttpClient.get<ApiResponse<Task?>>(
            "api/users/task/$taskId",
            null,
            headers
        )

        return apiResponse?.body
    }

    override suspend fun addSource(taskId: Long, source: String): ApiResponse<Source?>? {
        val token = dataStore.getToken()
        val headers = listOf(
            Header("Authorization", "Bearer $token"),
            Header("Content-Type", "application/json")
        )

        val sourceDto = SourceDto(source)

        val apiResponse =
            zHttpClient.post<ApiResponse<Source?>>(
                "api/teachers/task/$taskId/create-source",
                sourceDto,
                null,
                headers
            )

        return apiResponse?.body
    }

    override suspend fun getTaskSubmissions(taskId: Long): ApiResponse<List<Submission>?>? {
        val token = dataStore.getToken()
        val headers = listOf(
            Header("Authorization", "Bearer $token")
        )
        val apiResponse = zHttpClient.get<ApiResponse<List<Submission>?>>(
            "api/teachers/task/$taskId/submissions",
            null,
            headers
        )

        return apiResponse?.body


    }

    override suspend fun updateSubmission(
        submissionId: Long,
        link: String,
        grade: Int,
        additional: String
    ): ApiResponse<Submission?>? {
        val token = dataStore.getToken()
        val headers = listOf(
            Header("Authorization", "Bearer $token"),
            Header("Content-Type", "application/json")
        )

        val submissionDto = SubmissionDto(link, additional, grade)

        val apiResponse =
            zHttpClient.put<ApiResponse<Submission?>>(
                "api/teachers/update-submission/$submissionId",
                submissionDto,
                null,
                headers
            )

        return apiResponse?.body
    }
}