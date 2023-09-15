package com.thiagocontelli.watertracker.data

import androidx.room.Dao
import androidx.room.Insert
import com.thiagocontelli.watertracker.data.entities.Record

@Dao
interface RecordDao {
    @Insert
    suspend fun insert(record: Record)
}