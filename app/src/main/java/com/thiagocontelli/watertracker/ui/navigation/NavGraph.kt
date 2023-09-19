package com.thiagocontelli.watertracker.ui.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.thiagocontelli.watertracker.ui.features.main.MainScreen
import com.thiagocontelli.watertracker.ui.features.splash.SplashScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Splash.name) {
        composable(Screen.Splash.name) { SplashScreen(navController = navController) }
        composable(Screen.Main.name) { MainScreen() }
        composable(Screen.Startup.name) { Text(text = "Startup") }
    }
}