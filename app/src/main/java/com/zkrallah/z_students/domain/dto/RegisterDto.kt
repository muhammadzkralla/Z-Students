package com.zkrallah.z_students.domain.dto

data class RegisterDto(
    val email: String?,
    val password: String?,
    val firstName: String?,
    val lastName: String?
)