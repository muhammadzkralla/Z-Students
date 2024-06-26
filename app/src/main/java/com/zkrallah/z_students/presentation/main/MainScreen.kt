package com.zkrallah.z_students.presentation.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
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
import com.zkrallah.z_students.presentation.browse.BrowseScreen
import com.zkrallah.z_students.presentation.intro.OnBoarding
import com.zkrallah.z_students.presentation.intro.OnBoardingViewModel
import com.zkrallah.z_students.presentation.login.LoginScreen
import com.zkrallah.z_students.presentation.register.RegisterScreen
import com.zkrallah.z_students.presentation.request.RequestScreen
import com.zkrallah.z_students.presentation.reset.ConfirmResetScreen
import com.zkrallah.z_students.presentation.reset.ResetPasswordScreen
import com.zkrallah.z_students.presentation.task.TaskDetailsScreen
import com.zkrallah.z_students.presentation.task.TaskSubmissionsScreen
import com.zkrallah.z_students.presentation.task.UserTaskSubmissionsScreen
import com.zkrallah.z_students.presentation.user.UserScreen
import com.zkrallah.z_students.presentation.userclasses.UserClassesScreen
import com.zkrallah.z_students.presentation.userclasses.details.ClassDetailsScreen
import com.zkrallah.z_students.presentation.userclasses.details.RequestsScreen
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
                    NavigationBar {
                        SCREENS.forEach { item ->
                            val selected = item.route == navBackStackEntry?.destination?.route
                            NavigationBarItem(
                                selected = selected,
                                label = {
                                    Text(item.name)
                                },
                                onClick = {
                                    navController.navigate(item.route) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                },
                                icon = {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        if (selected) {
                                            Icon(
                                                painter = painterResource(id = item.selectedIcon),
                                                contentDescription = "Log Out"
                                            )
                                        } else {
                                            Icon(
                                                painter = painterResource(id = item.unSelectedIcon),
                                                contentDescription = "Log Out"
                                            )
                                        }
                                    }
                                }
                            )
                        }
                    }
                }
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier.padding(innerPadding)
            ) {
                Navigation(startingScreen, navController)
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Navigation(
    startingScreen: String,
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = startingScreen
    ) {
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
        composable(route = "Classes") {
            UserClassesScreen(
                navController = navController
            )
        }
        composable(
            route = "ClassDetails/{classId}/{className}",
            arguments = listOf(
                navArgument("classId") {
                    type = NavType.LongType
                },
                navArgument("className") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val classId = backStackEntry.arguments?.getLong("classId")
            val className = backStackEntry.arguments?.getString("className")
            ClassDetailsScreen(
                navController = navController,
                classId = classId!!,
                className = className!!
            )
        }
        composable(
            route = "ClassRequests/{classId}/{className}",
            arguments = listOf(
                navArgument("classId") {
                    type = NavType.LongType
                },
                navArgument("className") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val classId = backStackEntry.arguments?.getLong("classId")
            val className = backStackEntry.arguments?.getString("className")
            RequestsScreen(
                navController = navController,
                classId = classId!!,
                className = className!!
            )
        }
        composable(
            route = "TaskDetails/{taskId}/{taskName}",
            arguments = listOf(
                navArgument("taskId") {
                    type = NavType.LongType
                },
                navArgument("taskName") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getLong("taskId")
            val taskName = backStackEntry.arguments?.getString("taskName")
            TaskDetailsScreen(
                navController = navController,
                taskId = taskId!!,
                taskTitle = taskName!!
            )
        }
        composable(
            route = "TaskSubmissionsScreen/{taskId}/{taskName}",
            arguments = listOf(
                navArgument("taskId") {
                    type = NavType.LongType
                },
                navArgument("taskName") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getLong("taskId")
            val taskName = backStackEntry.arguments?.getString("taskName")
            TaskSubmissionsScreen(
                navController = navController,
                taskId = taskId!!,
                taskTitle = taskName!!
            )
        }
        composable(
            route = "UserTaskSubmissions/{taskId}",
            arguments = listOf(
                navArgument("taskId") {
                    type = NavType.LongType
                }
            )
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getLong("taskId")
            UserTaskSubmissionsScreen(
                navController = navController,
                taskId = taskId!!
            )
        }
        composable(route = "Requests") {
            RequestScreen()
        }
        composable(route = "User") {
            UserScreen(
                navController = navController
            )
        }
    }
}