package com.splashit.core.data.remote.model

import com.google.gson.annotations.SerializedName
import com.splashit.core.data.local.entity.PhotoEntity
import com.splashit.core.domain.entity.Photo

data class PhotoListResponse(
    val results: List<PhotoResponse>
)

data class PhotoResponse(
    val id: String,
    val createdAt: String,
    val width: Int,
    val height: Int,
    val description: String?,
    val altDescription: String?,
    @field:SerializedName("urls")
    val url: ImageUrlResponse,
    val likes: Int,
    val user: UserResponse
)

data class ImageUrlResponse(
    val small: String
)

fun List<PhotoResponse>.toLocal(): List<PhotoEntity> = this.map {
    PhotoEntity(
        it.id,
        it.createdAt,
        it.width,
        it.height,
        it.description,
        it.altDescription,
        it.url.small,
        it.likes,
        it.user.toLocal(),
        false
    )
}

fun List<PhotoResponse>.toDomain(): List<Photo> = this.map {
    Photo(
        it.id,
        it.createdAt,
        it.width,
        it.height,
        it.description,
        it.altDescription ?: "",
        it.url.small,
        it.likes,
        it.user.toDomain(),
        false
    )
}
