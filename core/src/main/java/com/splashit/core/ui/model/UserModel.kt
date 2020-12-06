package com.splashit.core.ui.model

import android.os.Parcelable
import com.splashit.core.domain.entity.User
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserModel(
    val name: String,
    val username: String,
    val profileImageUrl: String,
    val instagramUsername: String,
    val location: String
) : Parcelable

fun User.toPresentationModel(): UserModel = UserModel(
    name = this.name,
    username = this.username,
    profileImageUrl = this.profileImage,
    instagramUsername = this.instagramUsername,
    location = this.location
)

fun UserModel.toDomain(): User = User(
    name = this.name,
    username = this.username,
    profileImage = this.profileImageUrl,
    instagramUsername = this.instagramUsername,
    location = this.location
)