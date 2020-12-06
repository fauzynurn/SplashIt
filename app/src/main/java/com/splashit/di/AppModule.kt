package com.splashit.di

import com.splashit.core.domain.usecase.IPhotoUseCase
import com.splashit.core.domain.usecase.PhotoUseCase
import com.splashit.detail.DetailPhotoViewModel
import com.splashit.home.HomeViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<IPhotoUseCase> { PhotoUseCase(get()) }
}

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { DetailPhotoViewModel(get()) }
}