package com.splashit.core.data.remote.model

import com.google.gson.annotations.SerializedName
import com.splashit.core.data.local.entity.UserEntity
import com.splashit.core.domain.entity.User

data class UserResponse(
    @SerializedName("name")
    val name: String,
    @SerializedName("username")
    val username: String,
    @SerializedName("profile_image")
    val profileImage: ProfileImageResponse,
    @SerializedName("instagram_username")
    val instagramUsername: String?,
    @SerializedName("location")
    val location: String?
)

data class ProfileImageResponse(
    @SerializedName("medium")
    val medium: String
)

fun UserResponse.toDomain(): User = User(
    name = this.name,
    username = this.username,
    profileImage = this.profileImage.medium,
    instagramUsername = this.instagramUsername ?: "",
    location = this.location ?: ""
)

fun UserResponse.toLocal(): UserEntity = UserEntity(
    name = this.name,
    username = this.username,
    profileImage = this.profileImage.medium,
    instagramUsername = this.instagramUsername,
    location = this.location
)