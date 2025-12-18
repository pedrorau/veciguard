package com.pedrorau.veciguard.presentation.login

import androidx.lifecycle.ViewModel
import com.pedrorau.veciguard.domain.model.UserInfo
import com.pedrorau.veciguard.domain.usecase.GetUserInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val getUserInfoUseCase: GetUserInfoUseCase
): ViewModel() {

    private val _userInfo = MutableStateFlow<UserInfo>(
        value = TODO()
    )

    val userInfo: StateFlow<UserInfo> = _userInfo

    fun validateCredentials(username: String, password: String): Boolean {
        return username == "admin" && password == "1234"
    }
}