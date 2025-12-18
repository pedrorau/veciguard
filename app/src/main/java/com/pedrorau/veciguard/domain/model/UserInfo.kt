package com.pedrorau.veciguard.domain.model

data class UserInfo(
    val id: String,
    val name: String,
    val surname: String,
    val avatar: String,
    val type: UserType
)

enum class UserType {
    FREEMIUM, VIP
}
