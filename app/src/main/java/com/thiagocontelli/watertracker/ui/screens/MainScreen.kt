package com.thiagocontelli.watertracker.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thiagocontelli.watertracker.R
import com.thiagocontelli.watertracker.ui.components.CircularWaterProgress

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    Scaffold(topBar = { TopAppBar(title = {}) }) { paddingValues ->
        Column(Modifier.padding(paddingValues)) {
            Column(
                Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(64.dp)
            ) {
                CircularWaterProgress(percentage = 0.9f, number = 2000, radius = 125.dp)

                Column {
                    Text(
                        text = "Statistics",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    LazyColumn {
                        items(3) {
                            ElevatedCard(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 16.dp)
                            ) {
                                Row(
                                    Modifier
                                        .padding(16.dp)
                                        .fillMaxSize(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.baseline_water_drop_24),
                                        contentDescription = "Water drop"
                                    )

                                    Text(
                                        text = "Drank 200ml of water",
                                        style = MaterialTheme.typography.titleMedium
                                    )

                                    Text(
                                        text = "8pm", style = MaterialTheme.typography.labelLarge
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

    }
}

@Composable
@Preview(showSystemUi = true)
fun MainScreenPreview() {
    MainScreen()
}