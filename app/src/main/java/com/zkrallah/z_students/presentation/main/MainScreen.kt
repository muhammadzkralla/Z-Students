package com.zkrallah.z_students.presentation.main

import com.zkrallah.z_students.presentation.login.LoginScreen
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
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
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.zkrallah.z_students.domain.models.BottomNavItem
import com.zkrallah.z_students.presentation.intro.OnBoarding
import kotlinx.coroutines.runBlocking

val screens = listOf(
    BottomNavItem(
        "Home",
        "home",
        icon = Icons.Default.Home
    ),
    BottomNavItem(
        "Chat",
        "chat",
        icon = Icons.Default.Email
    ),
    BottomNavItem(
        "Settings",
        "settings",
        icon = Icons.Default.Settings
    )
)

val routes = listOf(
    "Home",
    "Settings",
    "Chat"
)

@Composable
fun MainScreen(mainViewModel: MainViewModel) {
    runBlocking {
        mainViewModel.getStartingDestination()
    }

    val startingDestination = mainViewModel.startingDestination.collectAsState()
    SetupNavigation(startingScreen = startingDestination.value, mainViewModel = mainViewModel)
}

@Composable
fun SetupNavigation(startingScreen: String, mainViewModel: MainViewModel) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        val navController = rememberNavController()
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        Scaffold(
            bottomBar = {
                if (routes.contains(navBackStackEntry?.destination?.route)) {
                    BottomNavigationBar(items = screens,
                        navController = navController,
                        onItemClick = {
                            navController.navigate(it.route)
                        }
                    )
                }
            }
        ) {
            Navigation(startingScreen, navController, Modifier.padding(it), mainViewModel)
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Navigation(
    startingScreen: String,
    navController: NavHostController,
    modifier: Modifier,
    mainViewModel: MainViewModel
) {
    NavHost(navController = navController, startDestination = startingScreen) {
        composable(route = "Home") {
            HomeScreen()
        }
        composable(route = "Chat") {
            ChatScreen()
        }
        composable(route = "Settings") {
            SettingsScreen()
        }
        composable(route = "Login") {
            LoginScreen(
                navController = navController
            )
        }
        composable(route = "OnBoarding") {
            OnBoarding(
                navController = navController,
                mainViewModel = mainViewModel
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

@Composable
fun SettingsScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Settings screen")
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