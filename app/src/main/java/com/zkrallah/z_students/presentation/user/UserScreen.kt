package com.zkrallah.z_students.presentation.user

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle
import com.zkrallah.z_students.R
import com.zkrallah.z_students.showToast
import com.zkrallah.z_students.util.cacheImageToFile
import com.zkrallah.z_students.util.getImageFileFromRealPath
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserScreen(
    navController: NavController,
    userViewModel: UserViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        userViewModel.getCurrentUser()
    }

    val getContent =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                Log.d("UserScreen", "Selected URI: $uri")
                val path = cacheImageToFile(context, uri)
                val file = getImageFileFromRealPath(path)
                userViewModel.uploadPhoto(file!!.path)
            } else {
                Log.d("UserScreen", "No media selected")
            }
        }

    val img = remember { mutableStateOf("") }
    val firstName = remember { mutableStateOf(TextFieldValue()) }
    val lastName = remember { mutableStateOf(TextFieldValue()) }
    val dob = remember { mutableStateOf(TextFieldValue()) }

    val getUserStatus = userViewModel.getUserStatus.collectAsState()
    val updateUserStatus = userViewModel.updateUserStatus.collectAsState()
    val uploadPhotoStatus = userViewModel.uploadPhotoStatus.collectAsState()

    val selectedDate = remember { mutableStateOf<LocalDate?>(LocalDate.now().minusDays(3)) }
    val calendarDialogState = rememberUseCaseState()
    val reverseFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")

    getUserStatus.value?.let { apiResponse ->
        if (apiResponse.success) {
            LaunchedEffect(Unit) {
                val user = apiResponse.data
                user?.let {
                    firstName.value = TextFieldValue(user.firstName ?: "")
                    lastName.value = TextFieldValue(user.lastName ?: "")
                    img.value = user.imageUrl ?: ""

                    if (!user.dob.isNullOrEmpty()) {
                        val date = LocalDate.parse(user.dob, reverseFormatter)
                        val formattedDate = date.format(formatter)
                        dob.value = TextFieldValue(formattedDate)
                    }
                }
            }
        } else showToast(context, apiResponse.message)
    }

    updateUserStatus.value?.let { apiResponse ->
        if (apiResponse.success) {
            LaunchedEffect(Unit) {
                showToast(context, "Updated")
                val user = apiResponse.data
                user?.let {
                    firstName.value = TextFieldValue(user.firstName ?: "")
                    lastName.value = TextFieldValue(user.lastName ?: "")
                    img.value = user.imageUrl ?: ""

                    if (!user.dob.isNullOrEmpty()) {
                        val date = LocalDate.parse(user.dob, reverseFormatter)
                        val formattedDate = date.format(formatter)
                        dob.value = TextFieldValue(formattedDate)
                    }
                }
            }
        } else showToast(context, apiResponse.message)
    }

    uploadPhotoStatus.value?.let { apiResponse ->
        if (apiResponse.success) {
            LaunchedEffect(Unit) {
                showToast(context, "Uploaded")
                val url = apiResponse.data
                url?.let {
                    img.value = url.message ?: ""
                }
            }
        } else showToast(context, apiResponse.message)
    }

    CalendarDialog(
        state = calendarDialogState,
        config = CalendarConfig(
            yearSelection = true,
            monthSelection = true,
            style = CalendarStyle.MONTH
        ),
        selection = CalendarSelection.Date(
            selectedDate = selectedDate.value
        ) { newDate ->
            selectedDate.value = newDate

            val date = LocalDate.parse(newDate.toString(), reverseFormatter)
            val formattedDate = date.format(formatter)

            dob.value = TextFieldValue(formattedDate)
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        Column(
            modifier = Modifier
                .fillMaxHeight(0.4f)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

            IconButton(
                onClick = {
                    userViewModel.logOut()
                    navController.navigate("Login") {
                        popUpTo(0)
                    }
                },
                modifier = Modifier
                    .align(Alignment.End)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_logout),
                    contentDescription = "Log Out"
                )
            }

            Box(
                modifier = Modifier
                    .padding(bottom = 16.dp)
            ) {
                if (img.value.isEmpty()) {
                    Image(
                        painter = painterResource(R.drawable.ic_person),
                        contentDescription = "User Photo",
                        modifier = Modifier
                            .size(150.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    CoilImage(
                        data = img.value,
                        contentDescription = null, // Provide content description as per your needs
                        modifier = Modifier
                            .size(150.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                }

                // Change photo button
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.Gray)
                        .clickable { /* Handle change photo */ }
                ) {
                    IconButton(
                        onClick = { getContent.launch("image/*") },
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_upload),
                            contentDescription = "Change Photo",
                            tint = Color.White,
                            modifier = Modifier
                                .padding(8.dp)
                        )
                    }
                }
            }
        }

        Row {
            TextField(
                value = firstName.value,
                onValueChange = { firstName.value = it },
                label = { Text("First Name") },
                modifier = Modifier.fillMaxWidth(0.5f)
            )

            Spacer(modifier = Modifier.width(8.dp))

            TextField(
                value = lastName.value,
                onValueChange = { lastName.value = it },
                label = { Text("Last Name") },
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = dob.value,
                onValueChange = { dob.value = it },
                label = { Text("Date of Birth") },
                modifier = Modifier.fillMaxWidth(0.5f),
                enabled = false
            )

            IconButton(
                onClick = { calendarDialogState.show() },
            ) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "Chose DOB"
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            OutlinedButton(
                onClick = {
                    userViewModel.updateUser(
                        firstName.value.text,
                        lastName.value.text,
                        dob.value.text
                    )
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Green
                ),
                shape = RoundedCornerShape(50),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
            ) {
                Text(
                    text = "Save",
                    color = Color.Black
                )
            }
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
fun PreviewUserScreen() {
    UserScreen(navController = rememberNavController())
}