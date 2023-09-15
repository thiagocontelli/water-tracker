package com.thiagocontelli.watertracker.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.thiagocontelli.watertracker.data.entities.Record

@Dao
interface RecordDao {
    @Insert
    suspend fun insert(record: Record)

    @Query("SELECT * FROM record WHERE DATE(created_at) = DATE('now', 'localtime') ORDER BY created_at DESC")
    suspend fun getTodays(): List<Record>
}