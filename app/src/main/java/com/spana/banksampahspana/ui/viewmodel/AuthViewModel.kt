package com.spana.banksampahspana.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.spana.banksampahspana.data.local.AuthPreferences
import com.spana.banksampahspana.data.model.Admin
import com.spana.banksampahspana.data.model.User
import com.spana.banksampahspana.data.repository.AuthRepository

class AuthViewModel(
    private val authRepository: AuthRepository,
    private val authPreferences: AuthPreferences
) : ViewModel() {

    fun login(email: String, password: String) = authRepository.login(email, password)

    fun register(user: User) = authRepository.register(user)

    fun registerAdmin(admin: Admin) = authRepository.registerAdmin(admin)

    fun userLogout() = authRepository.userLogout()

    fun getUserInfo() = authRepository.getUserInfo()

    fun getAuthUser(): LiveData<User> = authPreferences.getAuthUser().asLiveData()

    fun getAuthToken(): LiveData<String> = authPreferences.getAuthToken().asLiveData()

    fun updateUserInfo(user: User) = authRepository.updateUserInfo(user)

    fun updateUserAdmin(user: User) = authRepository.updateUserAdmin(user)

    fun getUsers() = authRepository.getUsers()

    fun getAdminInfo() = authRepository.getAdminInfo()

    fun adminLogout() = authRepository.adminLogout()

    fun downloadUserWithdrawalHistories() = authRepository.downloadUserWithdrawalHistories()

    fun downloadUserTransactionsByMonth(month: Int) =
        authRepository.downloadUserTransactionsByMonth(month)

    fun deleteUser(id: Int) = authRepository.deleteUser(id)

    fun updateAdminInfo(admin: Admin) = authRepository.updateAdminInfo(admin)

    fun changePassword(currentPassword: String, newPassword: String) =
        authRepository.changePassword(currentPassword, newPassword)

    fun forgotPassword(email: String) = authRepository.forgotPassword(email)

    fun resetPassword(token: String, email: String, password: String, passwordConfirm: String) =
        authRepository.resetPassword(token, email, password, passwordConfirm)
}