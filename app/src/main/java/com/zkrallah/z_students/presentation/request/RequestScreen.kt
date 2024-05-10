package com.zkrallah.z_students.presentation.request

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.zkrallah.z_students.showToast
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RequestScreen(
    requestViewModel: RequestViewModel = hiltViewModel()
) {
    val context = LocalContext.current


    val getRequestsStatus = requestViewModel.getRequestsStatus.collectAsState()
    val pullToRefreshState = rememberPullToRefreshState()
    val lazyListState = rememberLazyListState()

    Box(
        modifier = Modifier
            .nestedScroll(pullToRefreshState.nestedScrollConnection)
    ) {
        LazyColumn(
            state = lazyListState,
            modifier = Modifier
                .fillMaxSize()
        ) {
            getRequestsStatus.value?.let { apiResponse ->
                if (apiResponse.success) {
                    val requests = apiResponse.data?.reversed()

                    if (!requests.isNullOrEmpty()) {
                        items(requests) { item ->
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

        if (pullToRefreshState.isRefreshing) {
            LaunchedEffect(true) {
                requestViewModel.refresh()
                delay(1000L)
                pullToRefreshState.endRefresh()
            }
        }

        PullToRefreshContainer(
            state = pullToRefreshState,
            modifier = Modifier
                .align(Alignment.TopCenter),
        )
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