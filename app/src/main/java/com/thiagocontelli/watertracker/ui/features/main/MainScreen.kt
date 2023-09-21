package com.thiagocontelli.watertracker.ui.features.main

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
import androidx.compose.material3.AlertDialog
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.thiagocontelli.watertracker.R
import com.thiagocontelli.watertracker.ui.components.CircularWaterProgress
import com.thiagocontelli.watertracker.ui.navigation.Screen
import java.time.format.DateTimeFormatter

data class Option(
    val painterResourceId: Int,
    val contentDescription: String,
    val recipient: String,
    val amount: Int,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController, vm: MainViewModel = hiltViewModel()) {
    val state = vm.state.collectAsState().value

    val bottomSheetState = rememberModalBottomSheetState()

    val options = listOf(
        Option(R.drawable.glass_cup_icon, "Glass of water", "Glass", 200),
        Option(R.drawable.bottle_icon, "Bottle of water", "Bottle", 500),
        Option(R.drawable.gallon_icon, "Gallon of water", "Gallon", 1000),
    )

    val dateFormatter = DateTimeFormatter.ofPattern("hh:mm a")

    LaunchedEffect(key1 = true) {
        vm.getDailyGoal()
        vm.getStreak()
    }

    LaunchedEffect(key1 = state.showStreakCelebration) {
        if (state.showStreakCelebration) {
            navController.navigate(Screen.StreakCelebration.name + "/${state.streak}")
        }
    }

    Scaffold(topBar = {
        TopAppBar(title = { Text(text = "💧 ${state.streak}") }, actions = {
            TextButton(onClick = { vm.toggleModal() }) {
                Text(text = "New register")
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add new record",
                    modifier = Modifier.size(ButtonDefaults.IconSize)
                )
            }
        })
    }) { paddingValues ->
        Column(Modifier.padding(paddingValues)) {
            Column(
                Modifier
                    .padding(16.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(64.dp)
            ) {
                CircularWaterProgress(
                    percentage = if (state.dailyGoal > 0) state.todaysAmount.toFloat() / state.dailyGoal.toFloat() else 0f,
                    number = state.dailyGoal, radius = 125.dp
                )

                Column {
                    Text(
                        text = "Today's Statistics",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier
                            .padding(bottom = 16.dp)
                            .align(Alignment.Start)
                            .fillMaxWidth()
                    )

                    if (state.records.isEmpty()) {
                        Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                            Icon(painter = painterResource(id = R.drawable.no_water_icon), contentDescription = "No water", Modifier.size(64.dp))
                            Text("Nothing to show here!", style = MaterialTheme.typography.titleMedium)
                        }
                    } else {
                        LazyColumn {
                            items(state.records) { record ->
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
                                        Text(text = "💧")

                                        Text(
                                            text = "Drank ${getFormattedAmount(record.amount)} of water",
                                            style = MaterialTheme.typography.titleMedium
                                        )

                                        Text(
                                            text = dateFormatter.format(record.createdAt),
                                            style = MaterialTheme.typography.labelLarge
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

    if (state.showModal) {
        ModalBottomSheet(
            onDismissRequest = { vm.toggleModal() },
            sheetState = bottomSheetState,
        ) {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(32.dp)) {
                LazyRow(
                    Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround
                ) {
                    items(options) { option ->
                        Column(horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .clickable { vm.onAmountChange(option.amount) }
                                .padding(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Icon(
                                painterResource(id = option.painterResourceId),
                                contentDescription = option.contentDescription,
                                tint = if (option.amount == state.selectedAmount) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
                            )
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = option.recipient,
                                    color = if (option.amount == state.selectedAmount) MaterialTheme.colorScheme.primary else Color.Unspecified,
                                )
                                Text(
                                    text = getFormattedAmount(option.amount),
                                    color = if (option.amount == state.selectedAmount) MaterialTheme.colorScheme.primary else Color.Unspecified,
                                )
                            }
                        }
                    }
                }

                Button(
                    onClick = { vm.addRecord() },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !state.isLoading
                ) {
                    Text(text = if (state.isLoading) "Adding..." else "Add")
                }
            }
        }
    }

//    if (state.showStreakCelebration) {
//        AlertDialog(
//            onDismissRequest = { vm.dismissStreakCelebration() },
//            text = { Text(text = "Parabéns seu porra 👍") },
//            title = { Text(text = "🥳") },
//            confirmButton = {},
//            dismissButton = {
//                TextButton(onClick = { vm.dismissStreakCelebration() }) {
//                    Text("Dismiss")
//                }
//            }
//        )
//    }
}

fun getFormattedAmount(amount: Int): String {
    if (amount >= 1000) {
        return "${amount / 1000}L"
    }

    return "${amount}ml"
}