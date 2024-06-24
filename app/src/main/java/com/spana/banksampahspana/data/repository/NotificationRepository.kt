package com.spana.banksampahspana.data.repository

import androidx.lifecycle.liveData
import com.spana.banksampahspana.data.Result
import com.spana.banksampahspana.data.local.AuthPreferences
import com.spana.banksampahspana.data.remote.retrofit.ApiService
import retrofit2.HttpException

class NotificationRepository private constructor(
    private val apiService: ApiService,
    private val authPreferences: AuthPreferences
) {

    fun sendNotification(id: Int, title: String, body: String) = liveData {
        emit(Result.Loading)

        try {
            val notifResponse = apiService.sendNotification(id, title, body)

            if (notifResponse.isSuccessful) {
                emit(Result.Success(notifResponse.body()))
            } else {
                emit(Result.Error(notifResponse.message()))
            }

        } catch (e: HttpException) {
            emit(Result.Error(e.message()))
        }
    }

    fun updateToken(id: Int, token: String) = liveData {
        emit(Result.Loading)

        try {
            val tokenResponse = apiService.updateToken(id, token)

            if (tokenResponse.isSuccessful) {
                emit(Result.Success(tokenResponse.body()))
            } else {
                emit(Result.Error(tokenResponse.message()))
            }
        } catch (e: HttpException) {
            emit(Result.Error(e.message()))
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: NotificationRepository? = null

        @JvmStatic
        fun getInstance(
            apiService: ApiService,
            authPreferences: AuthPreferences
        ): NotificationRepository {
            return INSTANCE ?: synchronized(NotificationRepository::class.java) {
                val instance = NotificationRepository(apiService, authPreferences)
                INSTANCE = instance
                instance
            }
        }
    }
}