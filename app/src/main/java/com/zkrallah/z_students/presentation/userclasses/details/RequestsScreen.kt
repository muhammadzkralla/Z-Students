package com.zkrallah.z_students.presentation.userclasses.details

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.zkrallah.z_students.R
import com.zkrallah.z_students.presentation.userclasses.UserClassesViewModel
import com.zkrallah.z_students.showToast

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RequestsScreen(
    navController: NavController,
    userClassesViewModel: UserClassesViewModel = hiltViewModel(),
    classId: Long,
    className: String
) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        userClassesViewModel.getClassRequests(classId)
    }

    val classRequestsStatus = userClassesViewModel.classRequestsStatus.collectAsState()
    val requestResponseStatus = userClassesViewModel.requestResponseStatus.collectAsState()

    requestResponseStatus.value?.let { apiResponse ->
        if (apiResponse.success) {
            val request = apiResponse.data
            request?.let {
                showToast(context, "Request from ${request.user!!.email} is ${request.status}}!")
            }
        } else showToast(context, apiResponse.message)
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "$className Requests") },
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
            modifier = Modifier.fillMaxWidth()
                .padding(top = innerPadding.calculateTopPadding())
        ) {
            classRequestsStatus.value?.let { apiResponse ->
                if (apiResponse.success) {
                    val requests = apiResponse.data?.filter { it.status == "WAITING" }

                    if (!requests.isNullOrEmpty()) {
                        items(requests) {item ->
                            RequestItemCard(
                                email = item.user!!.email!!,
                                timestamp = item.timestamp!!,
                                status = item.status!!,
                                { userClassesViewModel.approveRequest(item.id!!) },
                                { userClassesViewModel.declineRequest(item.id!!) }
                            )
                        }
                    }
                } else showToast(context, apiResponse.message)
            }
        }
    }

}

@Composable
fun RequestItemCard(
    email: String,
    timestamp: String,
    status: String,
    onApprove: () -> Unit,
    onDecline: () -> Unit
) {
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
            text = timestamp,
            style = MaterialTheme.typography.bodyMedium
        )
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = status,
                style = MaterialTheme.typography.bodyMedium,
                color =
                when (status) {
                    "DECLINED" -> Color.Red
                    "APPROVED" -> Color.Green
                    else -> Color.Yellow
                }
            )

            Spacer(modifier = Modifier.weight(1f))

            IconButton(
                onClick = { onApprove() }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_tick),
                    contentDescription = "Log Out"
                )
            }
            IconButton(
                onClick = { onDecline() }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_cross),
                    contentDescription = "Log Out"
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCardItem() {
    RequestItemCard(email = "Title", timestamp = "2024-04-24 01:22:27.698", status = "APPROVED", {}, {})
}