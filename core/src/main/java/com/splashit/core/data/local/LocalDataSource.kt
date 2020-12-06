package com.splashit.core.data.local

import com.splashit.core.data.local.entity.PhotoEntity
import com.splashit.core.data.local.room.AppDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource(val dao: AppDao) {
    fun getAllPhoto(): Flow<List<PhotoEntity>> = dao.getAllPhoto()

    fun getFavoritePhoto(): Flow<List<PhotoEntity>> = dao.getFavoritePhoto()

    suspend fun insertPhoto(tourismList: List<PhotoEntity>) = dao.insertPhoto(tourismList)

    fun setFavoritePhoto(item: PhotoEntity) {
        dao.updateFavoritePhoto(item)
    }
}