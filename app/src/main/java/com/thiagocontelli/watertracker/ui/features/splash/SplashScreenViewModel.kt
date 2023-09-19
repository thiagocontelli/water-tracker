package com.thiagocontelli.watertracker.ui.features.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thiagocontelli.watertracker.data.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(private val dataStoreManager: DataStoreManager) :
    ViewModel() {

    private var _state = MutableStateFlow(State())
    val state: StateFlow<State> = _state

    fun getDailyGoal() {
        viewModelScope.launch {
            dataStoreManager.getDailyGoal().collect { dailyGoal ->
                _state.update { it.copy(dailyGoal = dailyGoal) }
            }
        }
    }
}