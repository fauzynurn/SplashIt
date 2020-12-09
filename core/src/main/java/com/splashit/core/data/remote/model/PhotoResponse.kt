package com.splashit.core.data.remote.model

import com.google.gson.annotations.SerializedName
import com.splashit.core.data.local.entity.PhotoEntity
import com.splashit.core.domain.entity.Photo

data class PhotoListResponse(
    @SerializedName("results")
    val results: List<PhotoResponse>
)

data class PhotoResponse(
    @SerializedName("id")
    val id: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("width")
    val width: Int,
    @SerializedName("height")
    val height: Int,
    @SerializedName("description")
    val description: String?,
    @SerializedName("alt_description")
    val altDescription: String?,
    @SerializedName("urls")
    val url: ImageUrlResponse,
    @SerializedName("likes")
    val likes: Int,
    @SerializedName("user")
    val user: UserResponse
)

data class ImageUrlResponse(
    @SerializedName("small")
    val small: String
)

fun List<PhotoResponse>.toLocal(): List<PhotoEntity> = this.let {
    it.map {
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
