package com.splashit.core.data

import com.splashit.core.data.local.LocalDataSource
import com.splashit.core.data.local.entity.fromDomain
import com.splashit.core.data.local.entity.toDomain
import com.splashit.core.data.remote.ApiResponse
import com.splashit.core.data.remote.RemoteDataSource
import com.splashit.core.data.remote.model.PhotoResponse
import com.splashit.core.data.remote.model.toDomain
import com.splashit.core.data.remote.model.toLocal
import com.splashit.core.domain.entity.Photo
import com.splashit.core.domain.repository.IPhotoRepository
import com.splashit.core.util.AppExecutor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class PhotoRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutor
) : IPhotoRepository {
    override fun getPhotoList(): Flow<Resource<List<Photo>>> =
        object : NetworkBoundResource<List<Photo>, List<PhotoResponse>>() {
            override fun loadFromDB(): Flow<List<Photo>> {
                return localDataSource.getAllPhoto().map {
                    it.toDomain()
                }
            }

            override fun shouldFetch(data: List<Photo>?): Boolean = data == null || data.isEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<PhotoResponse>>> =
                remoteDataSource.getPhotoList()

            override suspend fun saveCallResult(data: List<PhotoResponse>) {
                localDataSource.insertPhoto(data.toLocal())
            }

        }.asFlow()

    override fun getFavoritePhoto(): Flow<List<Photo>> =
        localDataSource.getFavoritePhoto().map { it.toDomain() }

    override fun setFavoritePhoto(photo: Photo) {
        appExecutors.diskIO().execute { localDataSource.setFavoritePhoto(photo.fromDomain()) }
    }

    override fun getUserPhoto(username: String): Flow<Resource<List<Photo>>> {
        return flow {
            emit(Resource.Loading())
            val response = remoteDataSource.getUserPhoto(username).first()
            when (response) {
                is ApiResponse.Success -> {
                    emit(Resource.Success(response.data.toDomain()))
                }
                is ApiResponse.Error -> {
                    emit(Resource.Error<List<Photo>>(response.errorMessage))
                }
                is ApiResponse.Empty -> {
                    emit(Resource.Success(emptyList<Photo>()))
                }
            }
        }
    }
}