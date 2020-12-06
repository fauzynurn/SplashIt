package com.splashit.favorite.di

import com.splashit.favorite.FavoriteViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val favModule = module {
    viewModel { FavoriteViewModel(get()) }
}