package com.splashit.core.data.local.entity

import com.splashit.core.domain.entity.User

data class UserEntity(
    val name: String,
    val username: String,
    val profileImage: String,
    val instagramUsername: String?,
    val location: String?
)

fun User.fromDomain(): UserEntity = UserEntity(
    name = this.name,
    username = this.username,
    profileImage = this.profileImage,
    instagramUsername = this.instagramUsername,
    location = this.location
)