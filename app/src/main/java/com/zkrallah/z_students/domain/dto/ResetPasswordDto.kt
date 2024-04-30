package com.zkrallah.z_students.domain.dto

data class ResetPasswordDto(
    val email: String?,
    val password: String?,
    val code: Int?
)
