package com.pedrorau.veciguard.presentation.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Report
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.pedrorau.veciguard.presentation.forgotPassword.ForgotPasswordScreen
import com.pedrorau.veciguard.presentation.home.HomeScreen
import com.pedrorau.veciguard.presentation.login.LoginScreen
import com.pedrorau.veciguard.presentation.map.MapScreen
import com.pedrorau.veciguard.presentation.notifications.NotificationsScreen
import com.pedrorau.veciguard.presentation.report.ReportScreen
import com.pedrorau.veciguard.presentation.settings.SettingsScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MainNavHost(
    onClose: () -> Unit = { },
) {
    val navController = rememberNavController()

    val screensWithBottomBar = listOf(
        Screens.Home.route,
        Screens.Map.route,
        Screens.Report.route,
        Screens.Settings.route,
    )

    val screensWithBackButton = listOf(
        Screens.Notifications.route,
        Screens.ForgotPassword.route,
        Screens.Map.route,
        Screens.Report.route,
        Screens.Settings.route,
    )

    val screenWithNotifications = Screens.Home.route

    val screensWithoutFAB = listOf(
        Screens.Home.route,
        Screens.ForgotPassword.route,
        Screens.Login.route
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold (
        topBar = {
            if (currentRoute in screensWithBackButton || currentRoute == screenWithNotifications) {
                TopAppBar(
                    title = {
                        Text(
                            text = when(currentRoute) {
                                Screens.Map.route -> "Mapa"
                                Screens.Report.route -> "Reportar Incidente"
                                Screens.Settings.route -> "Configuración"
                                Screens.ForgotPassword.route -> "Cambio de Contraseña"
                                Screens.Notifications.route -> "Notificaciones"
                                Screens.Home.route -> "Alerta Vecinal"
                                else -> ""
                            }
                        )
                    },
                    navigationIcon = {
                        if (currentRoute in screensWithBackButton) {
                            IconButton(onClick = { navController.navigateUp() }) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "Atrás"
                                )
                            }
                        }
                    },
                    actions = {
                        if (currentRoute == screenWithNotifications) {
                            IconButton(onClick = {
                                navController.navigate(Screens.Notifications.route)
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Notifications,
                                    contentDescription = "Notificaciones"
                                )
                            }
                        }
                    }
                )
            }
        },
        bottomBar = {
            if (currentRoute in screensWithBottomBar) {
                BottomNavigationBar(
                    navController = navController,
                    currentRoute = currentRoute
                )
            }
        },
        floatingActionButton = {
            if (currentRoute !in screensWithoutFAB) {
                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .shadow(
                            elevation = 16.dp,
                            shape = CircleShape,
                            spotColor = Color(0xFFE53935).copy(alpha = 0.3f)
                        )
                        .background(
                            color = Color(0xFFE53935),
                            shape = CircleShape
                        )
                        .clickable {
                            // Action
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "SOS",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 20.sp,
                        letterSpacing = 2.sp
                    )
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            modifier = Modifier.padding(paddingValues),
            navController = navController,
            startDestination = Screens.Login.route
        ) {
            composable(Screens.Login.route) {
                LoginScreen(
                    onBackButtonPressed = onClose,
                    onLogin = {
                        navController.navigate(Screens.Home.route)
                    },
                    onForgotPassword = {
                        navController.navigate(Screens.ForgotPassword.route)
                    },
                )
            }
            composable(Screens.ForgotPassword.route) {
                ForgotPasswordScreen(
                    onBackButtonPressed = {
                        navController.navigateUp()
                    },
                    onRememberCredentials = {
                        navController.navigateUp()
                    }
                )
            }
            composable(Screens.Home.route) {
                HomeScreen(
                    onBackButtonPressed = onClose,
                )
            }
            composable(Screens.Settings.route) {
                SettingsScreen(
                    onBackButtonPressed = {
                        navController.navigateUp()
                    }
                )
            }
            composable(Screens.Map.route) {
                MapScreen(
                    onBackButtonPressed = {
                        navController.navigateUp()
                    }
                )
            }
            composable(Screens.Report.route) {
                ReportScreen(
                    onBackButtonPressed = {
                        navController.navigateUp()
                    }
                )
            }
            composable(Screens.Notifications.route) {
                NotificationsScreen(
                    onBackButtonPressed = {
                        navController.navigateUp()
                    }
                )
            }
        }
    }
}


@Composable
fun BottomNavigationBar(
    navController: NavController,
    currentRoute: String?
) {
    NavigationBar {
        NavigationBarItem(
            selected = currentRoute == Screens.Home.route,
            onClick = {
                navController.navigate(Screens.Home.route) {
                    popUpTo(Screens.Home.route) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            icon = { Icon(Icons.Default.Home, contentDescription = null) },
            label = { Text("Inicio") }
        )

        NavigationBarItem(
            selected = currentRoute == Screens.Map.route,
            onClick = {
                navController.navigate(Screens.Map.route) {
                    popUpTo(Screens.Home.route) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            icon = { Icon(Icons.Default.Map, contentDescription = null) },
            label = { Text("Mapa") }
        )

        NavigationBarItem(
            selected = currentRoute == Screens.Report.route,
            onClick = {
                navController.navigate(Screens.Report.route) {
                    popUpTo(Screens.Home.route) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            icon = { Icon(Icons.Default.Report, contentDescription = null) },
            label = { Text("Reportar") }
        )

        NavigationBarItem(
            selected = currentRoute == Screens.Settings.route,
            onClick = {
                navController.navigate(Screens.Settings.route) {
                    popUpTo(Screens.Home.route) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            icon = { Icon(Icons.Default.Settings, contentDescription = null) },
            label = { Text("Perfil") }
        )
    }
}


sealed class Screens(val route: String) {
    data object Login: Screens("login")
    data object ForgotPassword: Screens("forgot_password")
    data object Home: Screens("home")
    data object Settings: Screens("settings")
    data object Map: Screens("map")
    data object Report: Screens("report")
    data object Notifications: Screens("notification")
}