package com.zkrallah.z_students.domain.response

data class ApiResponse<T>(
    val success: Boolean,
    val message: String,
    val data: T
)