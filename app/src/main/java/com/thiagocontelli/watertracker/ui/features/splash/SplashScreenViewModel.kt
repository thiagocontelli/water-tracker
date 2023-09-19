package com.thiagocontelli.watertracker.ui.features.splash

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thiagocontelli.watertracker.data.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(private val dataStoreManager: DataStoreManager) :
    ViewModel() {

    fun getDailyGoal(onSuccess: (dailyGoal: Int) -> Unit) {
        viewModelScope.launch {
            delay(1000)
            dataStoreManager.getDailyGoal().collect { dailyGoal ->
                onSuccess(dailyGoal)
            }
        }
    }
}