package com.zkrallah.z_students.domain.models

data class Submission(
    val id: Long?,
    val task: Task?,
    val user: User?,
    val grade: Int?,
    val additional: String?,
    val link: String?
)
