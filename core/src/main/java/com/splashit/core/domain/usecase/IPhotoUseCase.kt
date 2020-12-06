package com.splashit.core.domain.usecase

import com.splashit.core.data.Resource
import com.splashit.core.domain.entity.Photo
import kotlinx.coroutines.flow.Flow

interface IPhotoUseCase {
    fun getAllPhoto(): Flow<Resource<List<Photo>>>
    fun getFavoritePhoto(): Flow<List<Photo>>
    fun setFavoritePhoto(photo: Photo)
    fun getUserPhoto(username: String): Flow<Resource<List<Photo>>>
}