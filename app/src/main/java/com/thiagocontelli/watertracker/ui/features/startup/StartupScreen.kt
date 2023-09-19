package com.thiagocontelli.watertracker.ui.features.startup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.thiagocontelli.watertracker.R
import com.thiagocontelli.watertracker.ui.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StartupScreen(navController: NavController, vm: StartupViewModel = hiltViewModel()) {
    val state = vm.state.collectAsState().value

    Scaffold(topBar = { CenterAlignedTopAppBar(title = { Text(text = "Set your goal") }) }) { paddingValues ->
        Column(
            Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            TextField(
                value = state.goal,
                onValueChange = {},
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number, imeAction = ImeAction.Done
                ),
                textStyle = MaterialTheme.typography.headlineLarge.copy(textAlign = TextAlign.Center),
                singleLine = true,
                suffix = {
                    IconButton(onClick = { vm.increase() }) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
                    }
                },
                prefix = {
                    IconButton(onClick = { vm.decrease() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.remove_icon),
                            contentDescription = "Remove"
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                supportingText = { Text(text = "Max: ${vm.maxAmount}ml") },
                readOnly = true
            )

            Button(
                onClick = {
                    vm.storeGoal {
                        navController.popBackStack()
                        navController.navigate(Screen.Main.name)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp)
            ) {
                Text(text = "Done!")
            }
        }
    }
}