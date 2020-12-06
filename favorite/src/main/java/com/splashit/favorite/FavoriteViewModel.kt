package com.splashit.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.splashit.core.domain.usecase.IPhotoUseCase

class FavoriteViewModel(useCase: IPhotoUseCase) : ViewModel() {
    val favPhotoList = useCase.getFavoritePhoto().asLiveData()
}