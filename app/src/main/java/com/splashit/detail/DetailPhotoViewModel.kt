package com.splashit.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.splashit.core.data.Resource
import com.splashit.core.domain.entity.Photo
import com.splashit.core.domain.usecase.IPhotoUseCase
import com.splashit.core.ui.model.PhotoModel
import com.splashit.core.ui.model.toDomain

class DetailPhotoViewModel(private val useCase: IPhotoUseCase) : ViewModel() {
    fun setFavoritePhoto(photo: PhotoModel) : Boolean {
        photo.isFav = !photo.isFav
        useCase.setFavoritePhoto(photo.toDomain())
        return photo.isFav
    }

    fun getUserPhoto(username: String): LiveData<Resource<List<Photo>>> =
        useCase.getUserPhoto(username).asLiveData()
}