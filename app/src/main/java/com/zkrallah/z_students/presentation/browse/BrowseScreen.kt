package com.zkrallah.z_students.presentation.browse

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.zkrallah.z_students.showToast

@Composable
fun BrowseScreen(
    navController: NavController,
    browseViewModel: BrowseViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    browseViewModel.getClasses()
    browseViewModel.submitRequest(1L)

    val getClassesStatus = browseViewModel.getClassesStatus.collectAsState()
    val submitRequestStatus = browseViewModel.submitRequestStatus.collectAsState()

    getClassesStatus.value?.let { apiResponse ->
        if (apiResponse.success) {
            LaunchedEffect(Unit) {
                val classes = apiResponse.data
                classes?.let {
                    Log.d("BrowseScreen", "BrowseScreen: ${classes.size}")
                }
            }
        } else showToast(context, apiResponse.message)
    }

    submitRequestStatus.value?.let { apiResponse ->
        if (apiResponse.success) {
            LaunchedEffect(Unit) {
                val request = apiResponse.data
                request?.let {
                    Log.d("BrowseScreen", "BrowseScreen: $request")
                }
            }
        } else showToast(context, apiResponse.message)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

    }

}