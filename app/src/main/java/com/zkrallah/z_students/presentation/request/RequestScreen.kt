package com.zkrallah.z_students.presentation.request

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.zkrallah.z_students.showToast

@Composable
fun RequestScreen(
    requestViewModel: RequestViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    requestViewModel.getRequests()

    val getRequestsStatus = requestViewModel.getRequestsStatus.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
        getRequestsStatus.value?.let { apiResponse ->
            if (apiResponse.success) {
                val requests = apiResponse.data

                if (!requests.isNullOrEmpty()) {
                    items(requests) {item ->
                        RequestItemCard(
                            className = item.requestedClass?.name!!,
                            timestamp = item.timestamp!!,
                            status = item.status!!
                        )
                    }
                }
            } else showToast(context, apiResponse.message)
        }
    }

}

@Composable
fun RequestItemCard(
    className: String,
    timestamp: String,
    status: String
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
            text = className,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = timestamp,
            style = MaterialTheme.typography.bodyMedium
        )
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
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCardItem() {
    RequestItemCard(className = "Title", timestamp = "2024-04-24 01:22:27.698", status = "APPROVED")
}