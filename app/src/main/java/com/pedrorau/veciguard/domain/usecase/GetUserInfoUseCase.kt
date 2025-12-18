package com.pedrorau.veciguard.domain.usecase

import com.pedrorau.veciguard.data.repository.UserRepository
import com.pedrorau.veciguard.domain.mapper.toDomain
import com.pedrorau.veciguard.domain.model.UserInfo
import javax.inject.Inject

class GetUserInfoUseCase @Inject constructor(
    private val userRepository: UserRepository
){

    suspend operator fun invoke(): UserInfo {
        return userRepository.getUserInfo().toDomain()
    }
}