package com.splashit.core.data.remote.model

import com.splashit.core.data.local.entity.UserEntity
import com.splashit.core.domain.entity.User

data class UserResponse(
    val name: String,
    val username: String,
    val profileImage: ProfileImageResponse,
    val instagramUsername: String?,
    val location: String?
)

data class ProfileImageResponse(
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