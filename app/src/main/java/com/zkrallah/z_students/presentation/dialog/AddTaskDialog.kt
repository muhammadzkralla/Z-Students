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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: (String, String, String) -> Unit,
) {
    val titleState = remember { mutableStateOf(TextFieldValue()) }
    val descriptionState = remember { mutableStateOf(TextFieldValue()) }
    val dueState = remember { mutableStateOf(TextFieldValue()) }

    val selectedDate = remember { mutableStateOf<LocalDate?>(LocalDate.now().minusDays(3)) }
    val calendarDialogState = rememberUseCaseState()
    val reverseFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")

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
                    .background(color = MaterialTheme.colorScheme.surface, RoundedCornerShape(8.dp))
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Add Task")
                    Spacer(modifier = Modifier.height(8.dp))

                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = titleState.value,
                        onValueChange = { titleState.value = it },
                        label = { Text("Title...") },
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

                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = descriptionState.value,
                        onValueChange = { descriptionState.value = it },
                        label = { Text("Description...") },
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
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextField(
                            modifier = Modifier.fillMaxWidth(0.5f),
                            value = dueState.value,
                            onValueChange = { dueState.value = it },
                            label = { Text("Due..") },
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
                                    titleState.value.text,
                                    descriptionState.value.text,
                                    dueState.value.text
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

            dueState.value = TextFieldValue(formattedDate)
        }
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewAddTaskDialog() {
    AddTaskDialog({}) { _, _, _ ->

    }
}