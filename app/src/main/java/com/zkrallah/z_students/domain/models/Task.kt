package com.zkrallah.z_students.domain.models

data class Task(
    val id: Long?,
    val title: String?,
    val description: String?,
    val due: String?,
    val sources: List<Source>?,
    val targetedClass: Class?

)
