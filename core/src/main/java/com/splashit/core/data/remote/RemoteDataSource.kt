package com.splashit.core.data.remote

import com.splashit.core.BuildConfig
import com.splashit.core.data.remote.model.PhotoResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val service: ApiService) {
    suspend fun getPhotoList(): Flow<ApiResponse<List<PhotoResponse>>> {
        return flow {
            try {
                emit(ApiResponse.Success(service.getPhotoList(BuildConfig.API_KEY).results))
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