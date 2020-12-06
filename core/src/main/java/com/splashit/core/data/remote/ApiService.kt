package com.splashit.core.data.remote

import com.splashit.core.data.remote.model.PhotoListResponse
import com.splashit.core.data.remote.model.PhotoResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/photos")
    suspend fun getPhotoList(
        @Query("client_id") clientId: String,
        @Query("query") query: String = "mountain",
        @Query("orientation") orientation: String = "landscape"
    ): PhotoListResponse

    @GET("users/{username}/photos")
    suspend fun getUserPhoto(
        @Path("username") query: String,
        @Query("client_id") clientId: String
    ): List<PhotoResponse>
}