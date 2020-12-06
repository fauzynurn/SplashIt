package com.splashit.core.domain.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Photo(
    val id: String,
    val createdAt: String,
    val width: Int,
    val height: Int,
    val description: String?,
    val altDescription: String,
    val url: String,
    val likes: Int,
    val user: User,
    var isFav: Boolean
) : Parcelable