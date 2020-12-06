package com.splashit.core.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.splashit.core.data.local.entity.PhotoEntity

@Database(entities = [PhotoEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun appDao(): AppDao

}