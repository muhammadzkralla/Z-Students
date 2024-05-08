package com.zkrallah.z_students.presentation.task

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.zkrallah.z_students.R
import com.zkrallah.z_students.domain.models.Task
import com.zkrallah.z_students.presentation.dialog.AddSourceDialog
import com.zkrallah.z_students.presentation.dialog.AddSubmissionDialog
import com.zkrallah.z_students.showToast
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailsScreen(
    navController: NavController,
    taskDetailsViewModel: TaskDetailsViewModel = hiltViewModel(),
    taskId: Long,
    taskTitle: String
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        taskDetailsViewModel.getUserRole()
        taskDetailsViewModel.getTask(taskId)
        taskDetailsViewModel.getUserTaskSubmissions(taskId)

        taskDetailsViewModel.addSubmissionStatus.collectLatest { apiResponse ->
            apiResponse?.let {
                if (apiResponse.success) {
                    showToast(context, "Submission Added!")
                } else showToast(context, "Failed to Submit: ${apiResponse.message}")
            }
        }

        taskDetailsViewModel.addSourceStatus.collectLatest { apiResponse ->
            apiResponse?.let {
                if (apiResponse.success) {
                    showToast(context, "Source Added!")
                } else showToast(context, apiResponse.message)
            }
        }
    }

    val userRoleStatus = taskDetailsViewModel.userRoleStatus.collectAsState()

    val showAddSourceDialog = remember { mutableStateOf(false) }
    val showAddSubmissionDialog = remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = taskTitle) },
                navigationIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_back),
                        contentDescription = "Back Button",
                        modifier = Modifier
                            .padding(16.dp)
                            .clickable { navController.popBackStack() }
                    )
                },
                actions = {
                    if (userRoleStatus.value != "STUDENT") {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_submissions),
                            contentDescription = "Submissions",
                            modifier = Modifier
                                .padding(end = 16.dp)
                                .clickable { navController.navigate("TaskSubmissionsScreen/$taskId/$taskTitle") }
                        )
                    } else {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_submissions),
                            contentDescription = "Submissions",
                            modifier = Modifier
                                .padding(end = 16.dp)
                                .clickable { navController.navigate("UserTaskSubmissions/$taskId") }
                        )
                    }
                }
            )
        },
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            if (userRoleStatus.value != "STUDENT") {
                FloatingActionButton(
                    onClick = {
                        showAddSourceDialog.value = true
                    },
                    content = {
                        Icon(
                            imageVector = Icons.Rounded.Add,
                            contentDescription = "Post or Upload"
                        )
                    }
                )

            } else {
                FloatingActionButton(
                    onClick = {
                        showAddSubmissionDialog.value = true
                    },
                    content = {
                        Icon(
                            imageVector = Icons.Rounded.Add,
                            contentDescription = "Post or Upload"
                        )
                    }
                )
            }
        }
    ) { innerPadding ->

        var task by remember { mutableStateOf<Task?>(null) }

        LaunchedEffect(key1 = true) {
            taskDetailsViewModel.getTaskStatus.collectLatest { apiResponse ->
                apiResponse?.let {
                    if (apiResponse.success) {
                        task = apiResponse.data
                    } else showToast(context, apiResponse.message)
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = innerPadding.calculateTopPadding(), start = 16.dp)
        ) {
            Text(
                text = "Due: ${task?.due}",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.Red
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Description:",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = task?.description ?: "",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Sources: ${task?.sources?.size ?: 0}",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                if (!task?.sources.isNullOrEmpty()) {
                    items(task?.sources!!) { item ->
                        SourceItem(
                            source = item.source ?: ""
                        )
                    }
                }
            }
        }

        if (showAddSourceDialog.value) {
            AddSourceDialog(onDismissRequest = {
                showAddSourceDialog.value = false
            }) { source ->
                taskDetailsViewModel.addSource(taskId, source)
                showAddSourceDialog.value = false
            }
        }

        if (showAddSubmissionDialog.value) {
            AddSubmissionDialog(onDismissRequest = {
                showAddSubmissionDialog.value = false
            }) { link, additional ->
                taskDetailsViewModel.addSubmission(taskId, link, additional)
                showAddSubmissionDialog.value = false
            }
        }
    }

}

@Composable
fun SourceItem(
    source: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(color = Color.White, shape = RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Text(
            text = "source: $source",
            style = MaterialTheme.typography.bodyMedium
        )
    }

}