package com.zkrallah.z_students.presentation.userclasses.details

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.zkrallah.z_students.ClassTabs
import com.zkrallah.z_students.R
import com.zkrallah.z_students.presentation.intro.rememberPagerState
import com.zkrallah.z_students.presentation.userclasses.UserClassesViewModel
import com.zkrallah.z_students.showToast
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ClassDetailsScreen(
    navController: NavController,
    userClassesViewModel: UserClassesViewModel = hiltViewModel(),
    classId: Long,
    className: String
) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        userClassesViewModel.getUserRole()
    }

    val userRoleStatus = userClassesViewModel.userRoleStatus.collectAsState()

    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = ClassTabs.entries.size)
    val selectedTabIndex = remember { derivedStateOf { pagerState.currentPage } }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = className) },
                navigationIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_back),
                        contentDescription = "Back Button",
                        modifier = Modifier
                            .padding(16.dp)
                            .clickable { navController.popBackStack() }
                    )
                },
                actions = {
                    if (userRoleStatus.value != "STUDENT") {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_requests_filled),
                            contentDescription = "Class Requests",
                            modifier = Modifier
                                .padding(end = 16.dp)
                                .clickable {
                                    navController.navigate("ClassRequests/$classId/$className")
                                }
                        )
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = it.calculateTopPadding())
        ) {
            TabRow(
                selectedTabIndex = selectedTabIndex.value,
                modifier = Modifier.fillMaxWidth(),
                divider = {}
            ) {
                ClassTabs.entries.forEachIndexed { index, currentTab ->
                    Tab(
                        selected = selectedTabIndex.value == index,
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(currentTab.ordinal)
                            }
                        },
                        text = { Text(text = currentTab.text) },
                        icon = {
                            Icon(
                                painter = painterResource(
                                    id = if (selectedTabIndex.value == index)
                                        currentTab.selectedIcon else currentTab.unselectedIcon
                                ),
                                contentDescription = "Tab Icon"
                            )
                        }
                    )
                }
            }

            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) { page ->
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    when (page) {
                        0 -> TasksScreen(classId = classId)
                        1 -> AnnouncementsScreen(classId = classId)
                        2 -> MembersScreen(classId = classId)
                    }
                }
            }
        }
    }
}