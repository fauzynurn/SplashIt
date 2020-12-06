package com.splashit.core.data.local.room

import androidx.room.*
import com.splashit.core.data.local.entity.PhotoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {

    @Query("SELECT * FROM photo")
    fun getAllPhoto(): Flow<List<PhotoEntity>>

    @Query("SELECT * FROM photo where isFav = 1")
    fun getFavoritePhoto(): Flow<List<PhotoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhoto(tourism: List<PhotoEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateFavoritePhoto(tourism: PhotoEntity)
}