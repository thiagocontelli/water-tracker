package com.thiagocontelli.watertracker.ui.features.main

import com.thiagocontelli.watertracker.data.entities.Record

data class State(
    val selectedAmount: Int = 200,
    val isLoading: Boolean = false,
    val showModal: Boolean = false,
    val records: List<Record> = emptyList(),
    val todaysAmount: Int = 0,
    val dailyGoal: Int = 9999
)
