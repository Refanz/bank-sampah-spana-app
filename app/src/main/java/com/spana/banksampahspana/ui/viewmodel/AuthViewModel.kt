package com.spana.banksampahspana.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.spana.banksampahspana.data.local.AuthPreferences
import com.spana.banksampahspana.data.model.User
import com.spana.banksampahspana.data.repository.AuthRepository

class AuthViewModel(
    private val authRepository: AuthRepository,
    private val authPreferences: AuthPreferences
) : ViewModel() {

    fun login(email: String, password: String) = authRepository.login(email, password)

    fun register(user: User) = authRepository.register(user)

    fun logout() = authRepository.logout()

    fun getUserInfo() = authRepository.getUserInfo()

    fun getAuthUser(): LiveData<User> = authPreferences.getAuthUser().asLiveData()

    fun getAuthToken(): LiveData<String> = authPreferences.getAuthToken().asLiveData()
}