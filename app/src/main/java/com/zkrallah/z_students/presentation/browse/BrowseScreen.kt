package com.zkrallah.z_students.presentation.browse

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.zkrallah.z_students.R
import com.zkrallah.z_students.showToast
import kotlinx.coroutines.flow.collectLatest

@Composable
fun BrowseScreen(
    browseViewModel: BrowseViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        browseViewModel.getClasses()
    }

    val getClassesStatus = browseViewModel.getClassesStatus.collectAsState()

    LaunchedEffect(key1 = true) {
        browseViewModel.submitRequestStatus.collectLatest { apiResponse ->
            apiResponse?.let {
                if (apiResponse.success) {
                    val request = apiResponse.data
                    request?.let {
                        showToast(context, "Request to ${request.requestedClass!!.name} created!")
                    }
                } else showToast(context, apiResponse.message)
            }
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        getClassesStatus.value?.let { apiResponse ->
            if (apiResponse.success) {
                val classes = apiResponse.data

                if (!classes.isNullOrEmpty()) {
                    items(classes) {item ->
                        ClassItemCard(
                            className = item.name!!,
                            description = item.description!!,
                            numberOfMembers = item.numberOfUsers!!
                        ) {
                            browseViewModel.submitRequest(item.id!!)
                        }
                    }
                }
            } else showToast(context, apiResponse.message)
        }
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
            text = description,
            style = MaterialTheme.typography.bodyMedium
        )
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
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

            Spacer(modifier = Modifier.weight(1f))

            IconButton(
                onClick = { onItemClicked() }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_request),
                    contentDescription = "Log Out"
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewClassItemCard() {
    ClassItemCard(className = "Title", description = "Lorem Ipsum", numberOfMembers = 12) {

    }
}