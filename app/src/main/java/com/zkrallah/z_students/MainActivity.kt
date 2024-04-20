package com.zkrallah.z_students

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.google.accompanist.pager.ExperimentalPagerApi
import com.zkrallah.z_students.presentation.intro.OnBoarding
import com.zkrallah.z_students.ui.theme.ZStudentsTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalPagerApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ZStudentsTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    OnBoarding()
                }
            }
        }
    }
}