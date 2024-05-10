package com.zkrallah.z_students.presentation.userclasses

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.zkrallah.z_students.R
import com.zkrallah.z_students.showToast
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)
@Composable
fun UserClassesScreen(
    navController: NavController,
    userClassesViewModel: UserClassesViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val getClassesStatus = userClassesViewModel.getUserClassesStatus.collectAsState()
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
            getClassesStatus.value?.let { apiResponse ->
                if (apiResponse.success) {
                    val classes = apiResponse.data?.reversed()

                    if (!classes.isNullOrEmpty()) {
                        items(classes) { item ->
                            ClassItemCard(
                                className = item.name!!,
                                description = item.description!!,
                                numberOfMembers = item.numberOfUsers!!
                            ) {
                                navController.navigate("ClassDetails/${item.id}/${item.name}")
                            }
                        }
                    }
                } else showToast(context, apiResponse.message)
            }
        }

        if (pullToRefreshState.isRefreshing) {
            LaunchedEffect(true) {
                userClassesViewModel.refresh()
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
fun ClassItemCard(
    className: String,
    description: String,
    numberOfMembers: Int,
    onItemClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .clickable { onItemClicked() }
            .fillMaxWidth()
            .padding(16.dp)
            .background(color = MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(12.dp))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = className,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = description,
            style = MaterialTheme.typography.bodyMedium
        )
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_person),
                contentDescription = "Members",
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.width(4.dp))

            Text(
                text = "$numberOfMembers",
                style = MaterialTheme.typography.displaySmall,
                fontSize = (12.sp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewClassItemCard() {
    ClassItemCard(className = "Title", description = "Lorem Ipsum", numberOfMembers = 12) {

    }
}