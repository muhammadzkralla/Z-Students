package com.zkrallah.z_students.presentation.userclasses.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.navigation.NavController
import com.zkrallah.z_students.R
import com.zkrallah.z_students.presentation.userclasses.UserClassesViewModel
import com.zkrallah.z_students.showToast

@Composable
fun TasksScreen(
    navController: NavController,
    userClassesViewModel: UserClassesViewModel = hiltViewModel(),
    classId: Long
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        userClassesViewModel.getClassTasks(classId)
    }

    val classTasksStatus = userClassesViewModel.classTasksStatus.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        classTasksStatus.value?.let { apiResponse ->
            if (apiResponse.success) {
                val tasks = apiResponse.data?.reversed()

                if (!tasks.isNullOrEmpty()) {
                    items(tasks) {item ->
                        TaskCard(
                            title = item.title!!,
                            due = item.due!!,
                            sourcesCount = item.sources?.size ?: 0
                        ) {
                            navController.navigate("TaskDetails/${item.id}/${item.title}")
                        }
                    }
                }
            } else showToast(context, apiResponse.message)
        }
    }

}

@Composable
fun TaskCard(
    title: String,
    due: String,
    sourcesCount: Int,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
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
                text = "Due: $due",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Red
            )
            Text(
                text = "Sources: $sourcesCount",
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Image(
            painter = painterResource(R.drawable.ic_circle),
            contentDescription = "Tasks",
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
    }
}