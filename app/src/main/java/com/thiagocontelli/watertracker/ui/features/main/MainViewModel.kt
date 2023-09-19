package com.thiagocontelli.watertracker.ui.features.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thiagocontelli.watertracker.data.AppDatabase
import com.thiagocontelli.watertracker.data.entities.Record
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val appDatabase: AppDatabase) : ViewModel() {
    private var _state = MutableStateFlow(State())
    val state: StateFlow<State> = _state

    init {
        fetchRecords()
    }

    fun onAmountChange(amount: Int) {
        _state.update { it.copy(selectedAmount = amount) }
    }

    fun toggleModal() {
        _state.update { it.copy(showModal = !it.showModal) }
    }

    fun addRecord() {
        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            appDatabase.recordDao().insert(Record(amount = state.value.selectedAmount))
            fetchRecords()
        }
        _state.update { it.copy(isLoading = false) }
        toggleModal()
    }

    private fun fetchRecords() {
        viewModelScope.launch {
            val response = appDatabase.recordDao().getTodays()
            val amounts = response.map { it.amount }
            _state.update {
                it.copy(records = response, todaysAmount = if (amounts.isNotEmpty()) amounts.reduce { acc, i -> acc + i } else 0)
            }
        }
    }
}