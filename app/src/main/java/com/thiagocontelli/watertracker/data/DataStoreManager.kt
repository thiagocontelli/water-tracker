package com.thiagocontelli.watertracker.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import java.time.LocalDate

class DataStoreManager(context: Context) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "USER_DATA")
    private val dataStore = context.dataStore

    companion object {
        val DAILY_GOAL_KEY = intPreferencesKey("DAILY_GOAL")
        val STREAK_KEY = intPreferencesKey("STREAK")
        val STREAK_UPDATED_AT_KEY = stringPreferencesKey("STREAK_UPDATED_AT")
        const val DEFAULT_DAILY_GOAL = 0
        const val DEFAULT_STREAK = 0
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

    suspend fun increaseStreak(): Boolean {
        var x = false
        return dataStore.edit { userData ->
            if (!userData[STREAK_UPDATED_AT_KEY].equals(LocalDate.now().toString())) {
                userData[STREAK_KEY] = userData[STREAK_KEY]?.plus(1) ?: 1
                userData[STREAK_UPDATED_AT_KEY] = LocalDate.now().toString()
                x = true
            }
        }.run { x }
    }

    suspend fun resetStreak() {
        dataStore.edit { userData ->
            userData[STREAK_KEY] = 0
        }
    }

    fun getStreak(): Flow<Int> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { userData ->
                userData[STREAK_KEY] ?: DEFAULT_STREAK
            }
    }
}