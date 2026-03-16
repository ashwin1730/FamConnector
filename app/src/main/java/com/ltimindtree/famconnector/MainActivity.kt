package com.ltimindtree.famconnector

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ltimindtree.famconnector.ui.navigation.Screen
import com.ltimindtree.famconnector.ui.screens.*
import com.ltimindtree.famconnector.ui.theme.FamConnectorTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FamConnectorTheme {
                MainApp()
            }
        }
    }
}

@Composable
fun MainApp() {
    val navController = rememberNavController()
    var isOnboardingComplete by remember { mutableStateOf(false) } 

    if (!isOnboardingComplete) {
        OnboardingScreen(onFinished = { isOnboardingComplete = true })
    } else {
        val items = listOf(
            BottomNavItem("Hub", Screen.MyFamily.route, Icons.Default.People),
            BottomNavItem("Alerts", Screen.Alerts.route, Icons.Default.LocationOn),
            BottomNavItem("Feed", Screen.SightingFeed.route, Icons.Default.Visibility),
            BottomNavItem("Settings", Screen.Settings.route, Icons.Default.Settings),
        )

        Scaffold(
            bottomBar = {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                
                val showBottomBar = items.any { it.route == currentDestination?.route }
                
                AnimatedVisibility(
                    visible = showBottomBar,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    NavigationBar(
                        containerColor = MaterialTheme.colorScheme.surface,
                        tonalElevation = 8.dp
                    ) {
                        items.forEach { item ->
                            val isSelected = currentDestination?.hierarchy?.any { it.route == item.route } == true
                            NavigationBarItem(
                                icon = { 
                                    Icon(
                                        item.icon, 
                                        contentDescription = item.name,
                                        tint = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
                                    ) 
                                },
                                label = { 
                                    Text(
                                        item.name,
                                        style = MaterialTheme.typography.labelMedium.copy(
                                            fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Medium
                                        )
                                    ) 
                                },
                                selected = isSelected,
                                onClick = {
                                    navController.navigate(item.route) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                },
                                colors = NavigationBarItemDefaults.colors(
                                    indicatorColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
                                )
                            )
                        }
                    }
                }
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = Screen.MyFamily.route,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(Screen.MyFamily.route) {
                    MyFamilyScreen(
                        onAddProfile = { navController.navigate(Screen.ProfileEditor.createRoute()) },
                        onProfileClick = { id -> navController.navigate(Screen.ProfileDetail.createRoute(id)) }
                    )
                }
                composable(Screen.Alerts.route) {
                    AlertsNearMeScreen()
                }
                composable(Screen.SightingFeed.route) {
                    SightingFeedScreen()
                }
                composable(Screen.Settings.route) {
                    SettingsScreen()
                }
                composable(
                    route = Screen.ProfileDetail.route,
                    arguments = listOf(navArgument("profileId") { type = NavType.LongType })
                ) {
                    ProfileDetailScreen(
                        onNavigateBack = { navController.popBackStack() },
                        onEditProfile = { id -> navController.navigate(Screen.ProfileEditor.createRoute(id)) },
                        onMarkMissing = { id -> navController.navigate(Screen.CreateAlert.createRoute(id)) }
                    )
                }
                composable(
                    route = Screen.ProfileEditor.route,
                    arguments = listOf(navArgument("profileId") { type = NavType.LongType })
                ) {
                    ProfileEditorScreen(onNavigateBack = { navController.popBackStack() })
                }
                composable(
                    route = Screen.CreateAlert.route,
                    arguments = listOf(navArgument("profileId") { type = NavType.LongType })
                ) {
                    CreateAlertScreen(onNavigateBack = { navController.popBackStack() })
                }
                composable(Screen.SightingForm.route) {
                    SightingFormScreen(onNavigateBack = { navController.popBackStack() })
                }
            }
        }
    }
}

data class BottomNavItem(val name: String, val route: String, val icon: ImageVector)
