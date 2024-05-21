package com.zkrallah.z_students.presentation.reset

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.zkrallah.z_students.R
import com.zkrallah.z_students.showToast
import com.zkrallah.z_students.ui.theme.GreyDark
import com.zkrallah.z_students.ui.theme.GreyLight
import com.zkrallah.z_students.util.PasswordTextField

@Composable
fun ConfirmResetScreen(
    navController: NavController,
    resetPasswordViewModel: ResetPasswordViewModel = hiltViewModel(),
    email: String,
) {
    val context = LocalContext.current
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.reset))

    var otpValue by remember { mutableStateOf("") }
    val password = remember { mutableStateOf(TextFieldValue()) }
    val confirmPassword = remember { mutableStateOf(TextFieldValue()) }

    val resetStatus = resetPasswordViewModel.resetStatus.collectAsState()

    resetStatus.value?.let { apiResponse ->
        if (apiResponse.success) {
            navController.navigate("Login")
        } else showToast(context, apiResponse.message)
        resetPasswordViewModel.resetResetStatus()
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

        OtpTextField(
            otpText = otpValue,
            onOtpTextChange = { value, _ ->
                otpValue = value
            }
        )

        PasswordTextField(password = password)

        PasswordTextField(password = confirmPassword)

        OutlinedButton(
            onClick = {
                val newPassword = password.value.text
                val confirmation = confirmPassword.value.text

                if (newPassword.isNotEmpty() && newPassword == confirmation) {
                    resetPasswordViewModel.resetPassword(
                        email,
                        newPassword,
                        otpValue.toInt()
                    )
                } else showToast(context, "Password is empty or not matching!")
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
                text = "Confirm",
                color = Color.Black
            )
        }
    }

}

@Composable
fun OtpTextField(
    modifier: Modifier = Modifier,
    otpText: String,
    otpCount: Int = 6,
    onOtpTextChange: (String, Boolean) -> Unit
) {
    LaunchedEffect(Unit) {
        if (otpText.length > otpCount) {
            throw IllegalArgumentException("Otp text value must not have more than otpCount: $otpCount characters")
        }
    }

    BasicTextField(
        modifier = modifier,
        value = TextFieldValue(otpText, selection = TextRange(otpText.length)),
        onValueChange = {
            if (it.text.length <= otpCount) {
                onOtpTextChange.invoke(it.text, it.text.length == otpCount)
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
        decorationBox = {
            Row(horizontalArrangement = Arrangement.Center) {
                repeat(otpCount) { index ->
                    CharView(
                        index = index,
                        text = otpText
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
        }
    )
}

@Composable
private fun CharView(
    index: Int,
    text: String
) {
    val isFocused = text.length == index
    val char = when {
        index == text.length -> ""
        index > text.length -> ""
        else -> text[index].toString()
    }
    Text(
        modifier = Modifier
            .width(40.dp)
            .border(
                1.dp, when {
                    isFocused -> GreyDark
                    else -> GreyLight
                }, RoundedCornerShape(8.dp)
            )
            .padding(2.dp),
        text = char,
        style = MaterialTheme.typography.headlineMedium,
        color = if (isFocused) {
            GreyLight
        } else {
            GreyDark
        },
        textAlign = TextAlign.Center
    )
}