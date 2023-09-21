package com.thiagocontelli.watertracker.ui.features.streak_celebration

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.thiagocontelli.watertracker.R
import com.thiagocontelli.watertracker.ui.navigation.Screen

@Composable
fun StreakCelebration(navController: NavController, streak: Int) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.fill_water_animation))

    Column(
        Modifier
            .padding(16.dp)
            .fillMaxHeight()
    ) {
        Spacer(modifier = Modifier.weight(1f))

        Column(
            Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(contentAlignment = Alignment.Center) {
                LottieAnimation(
                    modifier = Modifier.size(275.dp),
                    composition = composition,
                    iterations = LottieConstants.IterateForever
                )

                Text(
                    text = streak.toString(),
                    style = MaterialTheme.typography.headlineLarge,
                    color = Color.White,
                    fontWeight = FontWeight.Black
                )
            }

            Text(
                text = "$streak Day${if (streak > 1) "s" else ""} Streak!",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Drink water every day to build your streak.",
                modifier = Modifier.padding(top = 8.dp),
                textAlign = TextAlign.Center
            )

        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                navController.popBackStack()
                navController.navigate(Screen.Main.name)
            },
            modifier = Modifier
                .fillMaxWidth(),
            shape = MaterialTheme.shapes.small
        ) {
            Text(text = "Continue".uppercase())
        }
    }
}