package com.pedrorau.veciguard.data.source

import com.pedrorau.veciguard.data.model.UserResponse

class MockUserDataSource: UserDataSource {

    private val userInfo = UserResponse(
        id = "",
        username = "",
        surname = "",
        avatar = "",
        type = ""
    )

    override suspend fun getUserInfo(): UserResponse {
        return userInfo
    }
}