package com.thiagocontelli.watertracker.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.thiagocontelli.watertracker.R
import com.thiagocontelli.watertracker.ui.navigation.Screen
import com.thiagocontelli.watertracker.ui.viewmodels.SplashScreenViewModel
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController, vm: SplashScreenViewModel = hiltViewModel()) {
    val state = vm.state.collectAsState().value

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.water_animation))

    LaunchedEffect(key1 = true) {
        vm.getDailyGoal()

        delay(1000)

        navController.popBackStack()

        if (state.dailyGoal > 0) {
            navController.navigate(Screen.Main.name)
        } else {
            navController.navigate(Screen.Startup.name)
        }
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        LottieAnimation(
            modifier = Modifier.size(350.dp),
            composition = composition,
            iterations = LottieConstants.IterateForever
        )
    }
}