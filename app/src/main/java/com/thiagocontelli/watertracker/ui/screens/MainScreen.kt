package com.thiagocontelli.watertracker.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.thiagocontelli.watertracker.ui.components.CircularWaterProgress

@Composable
fun MainScreen() {
    CircularWaterProgress(percentage = 0.9f, number = 2000, radius = 125.dp)
}