package com.spana.banksampahspana.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.spana.banksampahspana.data.local.AuthPreferences
import com.spana.banksampahspana.data.model.User
import com.spana.banksampahspana.data.repository.AuthRepository
import kotlinx.coroutines.launch

class AuthViewModel(
    private val authRepository: AuthRepository,
    private val authPreferences: AuthPreferences
) : ViewModel() {

    fun login(email: String, password: String) = authRepository.login(email, password)

    fun register(user: User) = authRepository.register(user)

    fun saveAuthToken(token: String) {
        viewModelScope.launch {
            authPreferences.saveAuthToken(token)
        }
    }

    fun getAuthToken(): LiveData<String> = authPreferences.getAuthToken().asLiveData()
}