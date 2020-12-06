package com.splashit.core.di

import androidx.room.Room
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.splashit.core.data.PhotoRepository
import com.splashit.core.data.local.LocalDataSource
import com.splashit.core.data.local.room.AppDatabase
import com.splashit.core.data.remote.ApiService
import com.splashit.core.data.remote.RemoteDataSource
import com.splashit.core.domain.repository.IPhotoRepository
import com.splashit.core.util.AppExecutor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    factory { get<AppDatabase>().appDao() }
    single {
        Room.inMemoryDatabaseBuilder(
            androidContext(),
            AppDatabase::class.java
            //, "Splashit.db"
        ).fallbackToDestructiveMigration().build()
    }
}

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.unsplash.com/")
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder().setFieldNamingPolicy(
                        FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES
                    ).create()
                )
            )
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    factory { AppExecutor() }
    single<IPhotoRepository> {
        PhotoRepository(
            get(),
            get(),
            get()
        )
    }
}