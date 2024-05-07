package com.zkrallah.z_students.presentation.task

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.zkrallah.z_students.R
import com.zkrallah.z_students.showToast

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskSubmissionsScreen(
    navController: NavController,
    taskDetailsViewModel: TaskDetailsViewModel = hiltViewModel(),
    taskId: Long,
    taskTitle: String
) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        taskDetailsViewModel.getTaskSubmissions(taskId)
    }

    val getTaskSubmissionsStatus = taskDetailsViewModel.getTaskSubmissionsStatus.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "$taskTitle Submissions") },
                navigationIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_back),
                        contentDescription = "Back Button",
                        modifier = Modifier
                            .padding(16.dp)
                            .clickable { navController.popBackStack() }
                    )
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = innerPadding.calculateTopPadding())
        ) {
            getTaskSubmissionsStatus.value?.let { apiResponse ->
                if (apiResponse.success) {
                    val submissions = apiResponse.data
                    if (!submissions.isNullOrEmpty()) {
                        items(submissions) { item ->
                            SubmissionItem(
                                email = item.user?.email ?: "",
                                link = item.link ?: "",
                                additional = item.additional ?: "",
                                grade = item.grade ?: 0
                            )
                        }
                    }
                } else showToast(context, apiResponse.message)
            }
        }
    }
}

@Composable
fun SubmissionItem(email: String, link: String, additional: String, grade: Int) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(color = Color.White, shape = RoundedCornerShape(12.dp))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = email,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Link: $link",
            style = MaterialTheme.typography.bodyMedium
        )

        Text(
            text = "Additional: $additional",
            style = MaterialTheme.typography.bodyMedium
        )

        Text(
            text = "Grade: $grade",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Red
        )
    }
}
