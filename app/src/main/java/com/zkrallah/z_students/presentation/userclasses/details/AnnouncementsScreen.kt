package com.zkrallah.z_students.presentation.userclasses.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.zkrallah.z_students.R
import com.zkrallah.z_students.presentation.userclasses.UserClassesViewModel
import com.zkrallah.z_students.showToast

@Composable
fun AnnouncementsScreen(
    userClassesViewModel: UserClassesViewModel = hiltViewModel(),
    classId: Long
) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        userClassesViewModel.getClassAnnouncements(classId)
    }

    val classAnnouncementsStatus = userClassesViewModel.classAnnouncementsStatus.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        classAnnouncementsStatus.value?.let { apiResponse ->
            if (apiResponse.success) {
                val classes = apiResponse.data

                if (!classes.isNullOrEmpty()) {
                    items(classes) {item ->
                        AnnouncementCard(
                            title = item.title!!,
                            content = item.content!!
                        )
                    }
                }
            } else showToast(context, apiResponse.message)
        }
    }

}

@Composable
fun AnnouncementCard(
    title: String,
    content: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(color = Color.White, shape = RoundedCornerShape(12.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(end = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = content,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Image(
            painter = painterResource(R.drawable.ic_announcements_filled),
            contentDescription = "User Photo",
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
    }
}