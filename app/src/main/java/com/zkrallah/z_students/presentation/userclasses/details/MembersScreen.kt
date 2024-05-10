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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.zkrallah.z_students.R
import com.zkrallah.z_students.presentation.userclasses.UserClassesViewModel
import com.zkrallah.z_students.showToast

@Composable
fun MembersScreen(
    userClassesViewModel: UserClassesViewModel = hiltViewModel(),
    classId: Long
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        userClassesViewModel.getClassMembers(classId)
    }

    val classMembersStatus = userClassesViewModel.classMembersStatus.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        classMembersStatus.value?.let { apiResponse ->
            if (apiResponse.success) {
                val classes = apiResponse.data

                if (!classes.isNullOrEmpty()) {
                    items(classes) { item ->
                        val role = item.authorities!![0]!!.authority
                        MemberCard(
                            firstName = item.firstName ?: "",
                            lastName = item.lastName ?: "",
                            role = role ?: "",
                            email = item.email ?: "",
                            imageUrl = item.imageUrl ?: ""
                        )
                    }
                }
            } else showToast(context, apiResponse.message)
        }
    }

}

@Composable
fun MemberCard(
    firstName: String,
    lastName: String,
    role: String,
    email: String,
    imageUrl: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(12.dp)
            )
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
                text = "$firstName $lastName",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = email,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = if (role == "TEACHER") "Teacher" else "Student",
                style = MaterialTheme.typography.bodyMedium
            )
        }

        if (imageUrl.isEmpty()) {
            Icon(
                painter = painterResource(R.drawable.ic_person),
                contentDescription = "User Photo",
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
            )
        } else {
            CoilImage(
                data = imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun CoilImage(
    data: String,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop
) {
    // Use Coil's rememberImagePainter to load and display the image
    val painter = rememberImagePainter(data = data, builder = {
        // You can apply transformations here if needed
    })
    Image(
        painter = painter,
        contentDescription = contentDescription,
        modifier = modifier,
        contentScale = contentScale
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewMemberCard() {
    MemberCard("John", "Doe", "TEACHER", "email@gmail.com", "")
}

