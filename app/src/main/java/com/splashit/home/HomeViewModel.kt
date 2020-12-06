package com.splashit.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.splashit.core.domain.usecase.IPhotoUseCase

class HomeViewModel(useCase: IPhotoUseCase) : ViewModel() {
    val photoList = useCase.getAllPhoto().asLiveData()
}