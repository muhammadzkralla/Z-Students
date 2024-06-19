package com.zkrallah.z_students

import android.content.Context
import android.widget.Toast
import com.zkrallah.z_students.domain.models.BottomNavItem

const val BASE_URL = "http://192.168.1.7:8080"

val SCREENS = listOf(
    BottomNavItem(
        "Classes",
        "Classes",
        selectedIcon = R.drawable.ic_groups_filled,
        unSelectedIcon = R.drawable.ic_groups_outlined
    ),
    BottomNavItem(
        "Browse",
        "Browse",
        selectedIcon = R.drawable.ic_browse_filled,
        unSelectedIcon = R.drawable.ic_browse_outlined
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

enum class ClassTabs(
    val selectedIcon: Int,
    val unselectedIcon: Int,
    val text: String
) {
    Tasks(
        unselectedIcon = R.drawable.ic_tasks_outlined,
        selectedIcon = R.drawable.ic_tasks_filled,
        text = "Tasks"
    ),
    Announcements(
        unselectedIcon = R.drawable.ic_announcements_outlined,
        selectedIcon = R.drawable.ic_announcements_filled,
        text = "Announcements"
    ),
    Members(
        unselectedIcon = R.drawable.ic_groups_outlined,
        selectedIcon = R.drawable.ic_groups_filled,
        text = "Members"
    )
}

fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}
