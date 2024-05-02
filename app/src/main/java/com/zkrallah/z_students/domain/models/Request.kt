package com.zkrallah.z_students.domain.models

data class Request(
    val id: Long?,
    val user: User?,
    val requestedClass: Class?,
    val status: String?,
    val timestamp: String?
)
