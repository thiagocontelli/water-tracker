package com.thiagocontelli.watertracker.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class DataStoreManager(context: Context) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "USER_DATA")
    private val dataStore = context.dataStore

    companion object {
        val DAILY_GOAL_KEY = intPreferencesKey("DAILY_GOAL")
        const val DEFAULT_DAILY_GOAL = 0
    }

    suspend fun setDailyGoal(dailyGoal: Int) {
        dataStore.edit { userData ->
            userData[DAILY_GOAL_KEY] = dailyGoal
        }
    }

    fun getDailyGoal(): Flow<Int> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { userData ->
                userData[DAILY_GOAL_KEY] ?: DEFAULT_DAILY_GOAL
            }
    }
}