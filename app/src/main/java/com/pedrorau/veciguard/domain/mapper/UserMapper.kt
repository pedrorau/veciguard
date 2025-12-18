package com.pedrorau.veciguard.domain.mapper

import com.pedrorau.veciguard.data.model.UserResponse
import com.pedrorau.veciguard.domain.model.UserInfo
import com.pedrorau.veciguard.domain.model.UserType

fun UserResponse.toDomain(): UserInfo {
    return UserInfo(
        id = id,
        name = username,
        surname = surname,
        avatar = avatar,
        type = if (type == "Free") UserType.FREEMIUM else UserType.VIP
    )
}