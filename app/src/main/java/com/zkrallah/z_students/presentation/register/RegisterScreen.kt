package com.zkrallah.z_students.presentation.register

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.zkrallah.z_students.R
import com.zkrallah.z_students.showToast
import com.zkrallah.z_students.util.PasswordTextField

@Composable
fun RegisterScreen(
    navController: NavController,
    registerViewModel: RegisterViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val email = remember { mutableStateOf(TextFieldValue()) }
    val password = remember { mutableStateOf(TextFieldValue()) }
    val firstName = remember { mutableStateOf(TextFieldValue()) }
    val lastName = remember { mutableStateOf(TextFieldValue()) }

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.login))

    val registerStatus = registerViewModel.registerStatus.collectAsState()

    registerStatus.value?.let { apiResponse ->
        Log.d("RegisterScreen", "RegisterScreen: $apiResponse")
        if (apiResponse.success) {
            registerViewModel.saveUser(apiResponse.data!!)
            val userEmail = email.value.text
            navController.navigate("Verification/${userEmail}")
        } else showToast(context, apiResponse.message)
        registerViewModel.resetRegisterStatus()
    }



    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        LottieAnimation(
            composition = composition,
            iterations = LottieConstants.IterateForever,
            modifier = Modifier
                .size(200.dp)
                .fillMaxWidth()
                .align(alignment = Alignment.CenterHorizontally)
        )

        OutlinedTextField(
            value = email.value,
            onValueChange = { email.value = it },
            label = { Text("Email") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        PasswordTextField(password = password)

        OutlinedTextField(
            value = firstName.value,
            onValueChange = { firstName.value = it },
            label = { Text("First Name") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        OutlinedTextField(
            value = lastName.value,
            onValueChange = { lastName.value = it },
            label = { Text("Last Name") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        OutlinedButton(
            onClick = {
                if (email.value.text.isNotEmpty()
                    && password.value.text.isNotEmpty()
                    && firstName.value.text.isNotEmpty()
                    && lastName.value.text.isNotEmpty()
                ) {
                    registerViewModel.registerStudent(
                        email.value.text,
                        password.value.text,
                        firstName.value.text,
                        lastName.value.text
                    )
                } else showToast(context, "Please add all the fields.")
            },
            shape = RoundedCornerShape(50),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text("Register as a Student")
        }

        OutlinedButton(
            onClick = {
                if (email.value.text.isNotEmpty()
                    && password.value.text.isNotEmpty()
                    && firstName.value.text.isNotEmpty()
                    && lastName.value.text.isNotEmpty()
                ) {
                    registerViewModel.registerTeacher(
                        email.value.text,
                        password.value.text,
                        firstName.value.text,
                        lastName.value.text
                    )
                } else showToast(context, "Please add all the fields.")
            },
            shape = RoundedCornerShape(50),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text("Register as a Teacher")
        }
    }
}
