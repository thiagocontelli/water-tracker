package com.thiagocontelli.watertracker.ui.features.startup

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
class StartupViewModel @Inject constructor(private val dataStoreManager: DataStoreManager) :
    ViewModel() {
    private var _state = MutableStateFlow(State())
    val state: StateFlow<State> = _state
    val maxAmount = 10000
    private val minAmount = 100
    private val step = 100

    fun increase() {
        if (_state.value.goal.isNotEmpty() && _state.value.goal.toInt() < maxAmount) {
            val goal = _state.value.goal.toInt() + step
            _state.update { it.copy(goal = goal.toString()) }
        }
    }

    fun decrease() {
        if (_state.value.goal.isNotEmpty() && _state.value.goal.toInt() > minAmount) {
            val goal = _state.value.goal.toInt() - step
            _state.update { it.copy(goal = goal.toString()) }
        }
    }

    fun storeGoal(onSuccess: () -> Unit) {
        viewModelScope.launch {
            dataStoreManager.setDailyGoal(_state.value.goal.toInt())
        }.run { onSuccess() }
    }
}