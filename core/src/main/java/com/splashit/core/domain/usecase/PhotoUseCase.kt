package com.splashit.core.domain.usecase

import com.splashit.core.data.Resource
import com.splashit.core.domain.entity.Photo
import com.splashit.core.domain.repository.IPhotoRepository
import kotlinx.coroutines.flow.Flow

class PhotoUseCase(val repository: IPhotoRepository) : IPhotoUseCase {
    override fun getAllPhoto(): Flow<Resource<List<Photo>>> = repository.getPhotoList()

    override fun getFavoritePhoto(): Flow<List<Photo>> = repository.getFavoritePhoto()

    override fun setFavoritePhoto(photo: Photo) = repository.setFavoritePhoto(photo)
    override fun getUserPhoto(username: String): Flow<Resource<List<Photo>>> =
        repository.getUserPhoto(username)
}