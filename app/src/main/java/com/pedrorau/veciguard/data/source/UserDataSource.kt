package com.pedrorau.veciguard.data.source

import com.pedrorau.veciguard.data.model.UserResponse

interface UserDataSource {

    suspend fun getUserInfo(): UserResponse
}