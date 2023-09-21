package com.thiagocontelli.watertracker.ui.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.thiagocontelli.watertracker.ui.features.main.MainScreen
import com.thiagocontelli.watertracker.ui.features.splash.SplashScreen
import com.thiagocontelli.watertracker.ui.features.startup.StartupScreen
import com.thiagocontelli.watertracker.ui.features.streak_celebration.StreakCelebration

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Splash.name) {
        composable(Screen.Splash.name) { SplashScreen(navController = navController) }
        composable(Screen.Main.name) { MainScreen(navController = navController) }
        composable(Screen.Startup.name) { StartupScreen(navController = navController) }
        composable(
            Screen.StreakCelebration.name + "/{streak}",
            arguments = listOf(navArgument("streak") { type = NavType.IntType })
        ) {
            StreakCelebration(navController = navController, streak = it.arguments?.getInt("streak") ?: 0)
        }
    }
}