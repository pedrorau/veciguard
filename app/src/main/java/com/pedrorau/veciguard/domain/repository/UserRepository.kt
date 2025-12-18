package com.pedrorau.veciguard.domain.repository

import com.pedrorau.veciguard.data.model.UserResponse
import com.pedrorau.veciguard.data.source.UserDataSource
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userDataSource: UserDataSource
){

    suspend fun getUserInfo(): UserResponse {
        return userDataSource.getUserInfo()
    }
}