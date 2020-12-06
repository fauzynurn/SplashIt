package com.splashit.core.domain.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val name: String,
    val username: String,
    val profileImage: String,
    val instagramUsername: String,
    val location: String
) : Parcelable