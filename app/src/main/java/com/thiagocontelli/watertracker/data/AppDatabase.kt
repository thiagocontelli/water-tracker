package com.thiagocontelli.watertracker.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.thiagocontelli.watertracker.data.entities.Record
import com.thiagocontelli.watertracker.utils.Converters

@Database(entities = [Record::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recordDao(): RecordDao
}