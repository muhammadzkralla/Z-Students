package com.zkrallah.z_students

import android.content.Context
import android.widget.Toast
import com.zkrallah.z_students.domain.models.BottomNavItem

val SCREENS = listOf(
    BottomNavItem(
        "Browse",
        "Browse",
        selectedIcon = R.drawable.ic_home_filled,
        unSelectedIcon = R.drawable.ic_home_outlined
    ),
    BottomNavItem(
        "Classes",
        "Classes",
        selectedIcon = R.drawable.ic_groups_filled,
        unSelectedIcon = R.drawable.ic_groups_outlined
    ),
    BottomNavItem(
        "Requests",
        "Requests",
        selectedIcon = R.drawable.ic_requests_filled,
        unSelectedIcon = R.drawable.ic_request_outlined
    ),
    BottomNavItem(
        "User",
        "User",
        selectedIcon = R.drawable.ic_account_filled,
        unSelectedIcon = R.drawable.ic_account_outlined
    )
)

val ROUTES = listOf(
    "Browse",
    "Classes",
    "Requests",
    "User"
)

const val BASE_URL = "http://192.168.1.11:8080"
const val LOGIN_ENDPOINT = "api/auth/login"
const val REGISTER_STUDENT_ENDPOINT = "api/auth/student/signup"
const val REGISTER_TEACHER_ENDPOINT = "api/auth/teacher/signup"
const val VERIFY_CODE = "api/auth/verify-code"
const val RESEND_CODE = "api/auth/regenerate-code"
const val RESET_PASSWORD = "api/auth/reset-password"

fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}
