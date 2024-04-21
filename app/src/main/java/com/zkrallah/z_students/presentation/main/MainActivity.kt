package com.zkrallah.z_students.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import com.google.accompanist.pager.ExperimentalPagerApi
import com.zkrallah.z_students.presentation.intro.OnBoarding
import com.zkrallah.z_students.ui.theme.ZStudentsTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()
    @OptIn(ExperimentalPagerApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            runBlocking {
                mainViewModel.isOnBoardingDone()
            }

            val isOnboardingDone = mainViewModel.onBoarding.collectAsState()

            CompositionLocalProvider {
                ZStudentsTheme {
                    if (isOnboardingDone.value) SetupNavigation()
                    else OnBoarding(mainViewModel::setOnBoardingDone)
                }
            }
        }
    }
}