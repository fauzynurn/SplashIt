package com.splashit.core.ui.model

import android.os.Parcelable
import com.splashit.core.domain.entity.Photo
import com.splashit.core.util.DateConverter
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PhotoModel(
    val id: String,
    val dimension: String,
    val createdDate: String,
    val description: String,
    val url: String,
    val likes: Int,
    val user: UserModel,
    var isFav: Boolean
) : Parcelable

fun Photo.toPresentationModel(): PhotoModel = PhotoModel(
    id = this.id,
    createdDate = DateConverter.formatDate(this.createdAt),
    description = this.description ?: this.altDescription,
    dimension = String.format("%sx%s", this.width, this.height),
    url = this.url,
    likes = this.likes,
    user = this.user.toPresentationModel(),
    isFav = this.isFav
)

fun PhotoModel.toDomain(): Photo {
    val splittedDimension = this.dimension.split("x").map { it.toInt() }
    return Photo(
        id = this.id,
        createdAt = this.createdDate,
        width = splittedDimension[0],
        height = splittedDimension[1],
        description = this.description,
        altDescription = this.description,
        url = this.url,
        likes = this.likes,
        user = this.user.toDomain(),
        isFav = this.isFav
    )
}