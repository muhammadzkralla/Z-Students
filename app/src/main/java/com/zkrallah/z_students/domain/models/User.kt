package com.zkrallah.z_students.domain.models

import com.google.gson.Gson
import java.util.Date

data class User(
    val accountNonExpired: Boolean?,
    val accountNonLocked: Boolean?,
    val authorities: List<Authority?>?,
    val code: Int?,
    val codeExpiredAt: String?,
    val createdAt: String?,
    val credentialsNonExpired: Boolean?,
    val dob: String?,
    val email: String?,
    val emailVerified: Boolean?,
    val enabled: Boolean?,
    val firstName: String?,
    val id: Long?,
    var imageUrl: String?,
    val lastName: String?,
    val password: String?,
    val username: String?
)

fun User.toJson(): String {
    return Gson().toJson(this)
}

// Convert JSON string to UserModel
fun String.toUserModel(): User {
    return Gson().fromJson(this, User::class.java)
}