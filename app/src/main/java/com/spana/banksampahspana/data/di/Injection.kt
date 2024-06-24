package com.spana.banksampahspana.data.di

import android.content.Context
import com.spana.banksampahspana.data.local.AuthPreferences
import com.spana.banksampahspana.data.local.dataStore
import com.spana.banksampahspana.data.remote.retrofit.ApiConfig
import com.spana.banksampahspana.data.repository.AuthRepository
import com.spana.banksampahspana.data.repository.NotificationRepository
import com.spana.banksampahspana.data.repository.TrashCategoryRepository
import com.spana.banksampahspana.data.repository.TrashRepository
import com.spana.banksampahspana.data.repository.WithdrawalRepository

object Injection {

    fun provideAuthRepository(context: Context): AuthRepository {
        val authPreferences = provideAuthPreferences(context)

        val apiService = ApiConfig.getApiService()
        return AuthRepository.getInstance(apiService, authPreferences)
    }

    fun provideAuthPreferences(context: Context): AuthPreferences {
        return AuthPreferences.getInstance(context.dataStore)
    }

    fun provideNotificationRepository(context: Context): NotificationRepository {
        val authPreferences = provideAuthPreferences(context)

        val apiService = ApiConfig.getApiService()
        return NotificationRepository.getInstance(apiService, authPreferences)
    }

    fun provideTrashCategoryRepository(context: Context): TrashCategoryRepository {
        val authPreferences = provideAuthPreferences(context)

        val apiService = ApiConfig.getApiService()
        return TrashCategoryRepository.getInstance(apiService, authPreferences)
    }

    fun provideTrashRepository(context: Context): TrashRepository {
        val authPreferences = provideAuthPreferences(context)

        val apiService = ApiConfig.getApiService()
        return TrashRepository.getInstance(apiService, authPreferences)
    }

    fun provideWithdrawalRepository(context: Context): WithdrawalRepository {
        val authPreferences = provideAuthPreferences(context)

        val apiService = ApiConfig.getApiService()
        return WithdrawalRepository.getInstance(apiService, authPreferences)
    }
}