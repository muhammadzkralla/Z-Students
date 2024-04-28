package com.zkrallah.z_students.domain.models

data class Token(
    val accessToken: String?,
    val accessTokenExpiresIn: String?,
    val refreshToken: String?,
    val refreshTokenExpiresIn: String?
)