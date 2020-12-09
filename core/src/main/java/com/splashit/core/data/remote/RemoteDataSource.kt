package com.splashit.core.data.remote

import com.splashit.core.BuildConfig
import com.splashit.core.data.remote.model.PhotoResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber

class RemoteDataSource(private val service: ApiService) {
    suspend fun getPhotoList(): Flow<ApiResponse<List<PhotoResponse>>> {
        return flow {
            try {
                val x = service.getPhotoList(BuildConfig.API_KEY)
                Timber.i("FROM DATASOURCEE: $x")
                emit(ApiResponse.Success(x.results))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getUserPhoto(username: String): Flow<ApiResponse<List<PhotoResponse>>> {
        return flow {
            try {
                emit(ApiResponse.Success(service.getUserPhoto(username, BuildConfig.API_KEY)))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }
}