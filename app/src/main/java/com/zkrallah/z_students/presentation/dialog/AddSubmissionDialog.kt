package com.zkrallah.z_students.presentation.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun AddSubmissionDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: (String, String) -> Unit,
) {
    val linkState = remember { mutableStateOf(TextFieldValue()) }
    val additionalState = remember { mutableStateOf(TextFieldValue()) }

    Dialog(
        onDismissRequest = {
            onDismissRequest()
        },
        properties = DialogProperties(
            dismissOnClickOutside = true,
            dismissOnBackPress = true,
            usePlatformDefaultWidth = false
        )
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .background(Color.White, RoundedCornerShape(8.dp))
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Add Submission")

                    Spacer(modifier = Modifier.height(8.dp))

                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = linkState.value,
                        onValueChange = { linkState.value = it },
                        label = { Text("Link...") },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Text
                        ),
                        colors = TextFieldDefaults.colors(
                            disabledTextColor = Color.Gray,
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            disabledContainerColor = Color.Gray,
                            focusedIndicatorColor = Color.Red,
                            unfocusedIndicatorColor = Color.Black,
                            disabledIndicatorColor = Color.Transparent
                        )
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = additionalState.value,
                        onValueChange = { additionalState.value = it },
                        label = { Text("Additional...") },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Text
                        ),
                        colors = TextFieldDefaults.colors(
                            disabledTextColor = Color.Gray,
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            disabledContainerColor = Color.Gray,
                            focusedIndicatorColor = Color.Red,
                            unfocusedIndicatorColor = Color.Black,
                            disabledIndicatorColor = Color.Transparent
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        TextButton(
                            onClick = { onDismissRequest() },
                            modifier = Modifier.padding(8.dp),
                        ) {
                            Text("Dismiss")
                        }
                        TextButton(
                            onClick = {
                                onConfirmation(
                                    linkState.value.text,
                                    additionalState.value.text
                                )
                            },
                            modifier = Modifier.padding(8.dp),
                        ) {
                            Text("Confirm")
                        }
                    }
                }
            }
        }
    }

}