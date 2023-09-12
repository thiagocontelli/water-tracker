package com.thiagocontelli.watertracker.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thiagocontelli.watertracker.R
import com.thiagocontelli.watertracker.ui.components.CircularWaterProgress

data class Option(
    val id: Int,
    val painterResourceId: Int,
    val contentDescription: String,
    val recipient: String,
    val amount: Int,
    var selected: Boolean
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    var showModal by remember {
        mutableStateOf(false)
    }

    val bottomSheetState = rememberModalBottomSheetState()

    var options by remember {
        mutableStateOf(
            listOf(
                Option(1, R.drawable.glass_cup, "Glass of water", "Glass", 200, true),
                Option(2, R.drawable.bottle, "Bottle of water", "Bottle", 500, false),
                Option(3, R.drawable.gallon, "Gallon of water", "Gallon", 1000, false),
            )
        )
    }

    Scaffold(topBar = {
        TopAppBar(title = {}, actions = {
            TextButton(onClick = { showModal = true }) {
                Text(text = "New register")
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add new register",
                    modifier = Modifier.size(ButtonDefaults.IconSize)
                )
            }
        })
    }) { paddingValues ->
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

    if (showModal) {
        ModalBottomSheet(
            onDismissRequest = { showModal = false },
            sheetState = bottomSheetState,
        ) {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(32.dp)) {
                LazyRow(
                    Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround
                ) {
                    items(options) { option ->
                        Column(horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .clickable {
                                    options = options.map {
                                        if (it.id == option.id) it.copy(selected = true) else it.copy(
                                            selected = false
                                        )
                                    }
                                }
                                .padding(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Icon(
                                painterResource(id = option.painterResourceId),
                                contentDescription = option.contentDescription,
                                tint = if (option.selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
                            )
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = option.recipient,
                                    color = if (option.selected) MaterialTheme.colorScheme.primary else Color.Unspecified,
                                )
                                Text(
                                    text = "${option.amount}ml",
                                    color = if (option.selected) MaterialTheme.colorScheme.primary else Color.Unspecified,
                                )
                            }
                        }
                    }
                }

                Button(onClick = { /*TODO*/ }, modifier = Modifier.fillMaxWidth()) {
                    Text(text = "Add")
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