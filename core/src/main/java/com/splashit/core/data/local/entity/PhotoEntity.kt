package com.splashit.core.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.splashit.core.domain.entity.Photo
import com.splashit.core.domain.entity.User

@Entity(tableName = "photo")
data class PhotoEntity(
    @PrimaryKey
    val id: String,
    val createdAt: String?,
    val width: Int,
    val height: Int,
    val description: String?,
    @ColumnInfo(name = "altdescription")
    val altDescription: String?,
    val url: String,
    val likes: Int,
    @Embedded
    val user: UserEntity,
    var isFav: Boolean
)

fun List<PhotoEntity>.toDomain(): List<Photo> = this.map {
    Photo(
        it.id,
        it.createdAt ?: "",
        it.width,
        it.height,
        it.description ?: "",
        it.altDescription ?: "",
        it.url,
        it.likes,
        User(
            it.user.name,
            it.user.username,
            it.user.profileImage,
            it.user.instagramUsername ?: "",
            it.user.location ?: ""
        ),
        it.isFav
    )
}

fun Photo.fromDomain(): PhotoEntity =
    PhotoEntity(
        this.id,
        this.createdAt,
        this.width,
        this.height,
        this.description,
        this.altDescription,
        this.url,
        this.likes,
        this.user.fromDomain(),
        this.isFav
    )