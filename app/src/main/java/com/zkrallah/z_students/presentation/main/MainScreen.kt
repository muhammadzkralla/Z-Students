package com.zkrallah.z_students.presentation.main

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.pager.ExperimentalPagerApi
import com.zkrallah.z_students.ROUTES
import com.zkrallah.z_students.SCREENS
import com.zkrallah.z_students.domain.models.BottomNavItem
import com.zkrallah.z_students.presentation.browse.BrowseScreen
import com.zkrallah.z_students.presentation.intro.OnBoarding
import com.zkrallah.z_students.presentation.intro.OnBoardingViewModel
import com.zkrallah.z_students.presentation.login.LoginScreen
import com.zkrallah.z_students.presentation.register.RegisterScreen
import com.zkrallah.z_students.presentation.reset.ConfirmResetScreen
import com.zkrallah.z_students.presentation.reset.ResetPasswordScreen
import com.zkrallah.z_students.presentation.user.UserScreen
import com.zkrallah.z_students.presentation.verification.VerificationScreen
import kotlinx.coroutines.runBlocking

@Composable
fun MainScreen(onBoardingViewModel: OnBoardingViewModel = hiltViewModel()) {
    runBlocking {
        onBoardingViewModel.getStartingDestination()
    }

    val startingDestination = onBoardingViewModel.startingDestination.collectAsState()
    SetupNavigation(startingScreen = startingDestination.value)
}

@Composable
fun SetupNavigation(startingScreen: String) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        val navController = rememberNavController()
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        Scaffold(
            bottomBar = {
                if (ROUTES.contains(navBackStackEntry?.destination?.route)) {
                    BottomNavigationBar(items = SCREENS,
                        navController = navController,
                        onItemClick = {
                            navController.navigate(it.route) {
                                popUpTo(0)
                            }
                        }
                    )
                }
            }
        ) {
            Navigation(startingScreen, navController, Modifier.padding(it))
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Navigation(
    startingScreen: String,
    navController: NavHostController,
    modifier: Modifier
) {
    NavHost(navController = navController, startDestination = startingScreen) {
        composable(route = "OnBoarding") {
            OnBoarding(
                navController = navController
            )
        }
        composable(route = "Login") {
            LoginScreen(
                navController = navController
            )
        }
        composable(route = "Register") {
            RegisterScreen(
                navController = navController
            )
        }
        composable(
            route = "Verification/{email}",
            arguments = listOf(
                navArgument("email") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email")
            VerificationScreen(
                navController = navController,
                email = email!!
            )
        }
        composable(route = "ResetPassword") {
            ResetPasswordScreen(
                navController = navController
            )
        }
        composable(
            route = "ConfirmReset/{email}",
            arguments = listOf(
                navArgument("email") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email")
            ConfirmResetScreen(
                navController = navController,
                email = email!!
            )
        }
        composable(route = "Browse") {
            BrowseScreen()
        }
        composable(route = "Chat") {
            ChatScreen()
        }
        composable(route = "User") {
            UserScreen(
                navController = navController
            )
        }
    }
}

@Composable
fun HomeScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Home screen", fontSize = 20.sp)
    }
}

@Composable
fun ChatScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Chat screen")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavigationBar(
    items: List<BottomNavItem>,
    navController: NavController,
    modifier: Modifier = Modifier,
    onItemClick: (BottomNavItem) -> Unit,
) {
    val backStackEntry = navController.currentBackStackEntryAsState()

    BottomNavigation(
        modifier = modifier,
        elevation = 5.dp
    ) {

        items.forEach { item ->
            val selected = item.route == backStackEntry.value?.destination?.route
            BottomNavigationItem(
                selected = selected,
                onClick = { onItemClick(item) },
                selectedContentColor = Color.Magenta,
                unselectedContentColor = MaterialTheme.colorScheme.onBackground,
                icon = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Log.d("TAG", "BottomNavigationBar: ${item.badgeCount}")
                        if (item.badgeCount > 0) {
                            BadgedBox(badge = {
                                Text(text = item.badgeCount.toString())
                            }) {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = item.name
                                )
                            }
                        } else {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.name
                            )
                        }

                        if (selected) {
                            Text(
                                text = item.name,
                                textAlign = TextAlign.Center,
                                fontSize = 10.sp
                            )
                        }
                    }
                })
        }
    }
}