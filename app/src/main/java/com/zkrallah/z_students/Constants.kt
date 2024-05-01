package com.zkrallah.z_students

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import com.zkrallah.z_students.domain.models.BottomNavItem

val SCREENS = listOf(
    BottomNavItem(
        "Home",
        "home",
        icon = Icons.Default.Home
    ),
    BottomNavItem(
        "Chat",
        "chat",
        icon = Icons.Default.Email
    ),
    BottomNavItem(
        "Settings",
        "settings",
        icon = Icons.Default.Settings
    )
)

val ROUTES = listOf(
    "Home",
    "Settings",
    "Chat"
)

const val BASE_URL = "http://192.168.1.8:8080"
const val LOGIN_ENDPOINT = "api/auth/login"
const val REGISTER_STUDENT_ENDPOINT = "api/auth/student/signup"
const val REGISTER_TEACHER_ENDPOINT = "api/auth/teacher/signup"
const val VERIFY_CODE = "api/auth/verify-code"
const val RESEND_CODE = "api/auth/regenerate-code"
const val RESET_PASSWORD = "api/auth/reset-password"