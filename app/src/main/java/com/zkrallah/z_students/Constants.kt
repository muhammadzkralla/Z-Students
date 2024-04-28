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

const val BASE_URL = "http://192.168.1.5:8080"
const val LOGIN_ENDPOINT = "api/auth/login"