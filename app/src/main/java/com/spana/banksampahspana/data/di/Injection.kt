package com.spana.banksampahspana.data.di

import android.app.Application
import com.spana.banksampahspana.data.local.AuthPreferences
import com.spana.banksampahspana.data.local.dataStore
import com.spana.banksampahspana.data.remote.retrofit.ApiConfig
import com.spana.banksampahspana.data.repository.AuthRepository
import com.spana.banksampahspana.data.repository.TrashCategoryRepository
import com.spana.banksampahspana.data.repository.TrashRepository

object Injection {

    fun provideAuthRepository(): AuthRepository {
        val apiService = ApiConfig.getApiService()
        return AuthRepository.getInstance(apiService)
    }

    fun provideAuthPreferences(mApplication: Application): AuthPreferences {
        return AuthPreferences.getInstance(mApplication.dataStore)
    }

    fun provideTrashCategoryRepository(): TrashCategoryRepository {
        val apiService = ApiConfig.getApiService()
        return TrashCategoryRepository.getInstance(apiService)
    }

    fun provideTrashRepository(): TrashRepository {
        val apiService = ApiConfig.getApiService()
        return TrashRepository.getInstance(apiService)
    }
}