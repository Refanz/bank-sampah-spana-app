package com.spana.banksampahspana.data.repository

import android.util.Log
import androidx.lifecycle.liveData
import com.spana.banksampahspana.data.Result
import com.spana.banksampahspana.data.local.AuthPreferences
import com.spana.banksampahspana.data.remote.retrofit.ApiService
import kotlinx.coroutines.flow.first
import retrofit2.HttpException

class WithdrawalRepository private constructor(
    private val apiService: ApiService,
    private val authPreferences: AuthPreferences
) {

    fun withdrawal(totalWithdrawal: Int) = liveData {
        emit(Result.Loading)

        try {
            val token = authPreferences.getAuthToken().first()
            val withdrawalResponse = apiService.withdrawal("Bearer $token", totalWithdrawal)

            if (withdrawalResponse.isSuccessful) {
                emit(Result.Success(withdrawalResponse.body()?.data))
            } else {
                emit(Result.Error(withdrawalResponse.errorBody().toString()))
            }
        } catch (e: HttpException) {
            emit(Result.Error(e.message()))
            Log.e(TAG, e.message())
        }
    }

    fun getUserTotalWithdrawal() = liveData {
        emit(Result.Loading)

        try {
            val token = authPreferences.getAuthToken().first()
            val totalWithdrawalResponse = apiService.getUserTotalWithdrawal("Bearer $token")

            if (totalWithdrawalResponse.isSuccessful) {
                emit(Result.Success(totalWithdrawalResponse.body()?.total))
            } else {
                emit(Result.Error(totalWithdrawalResponse.errorBody().toString()))
            }

        } catch (e: HttpException) {
            emit(Result.Error(e.message()))
        }
    }

    fun getUserWithdrawalHistories() = liveData {
        emit(Result.Loading)

        try {
            val token = authPreferences.getAuthToken().first()
            val withdrawalResponse = apiService.getUserWithdrawalHistories("Bearer $token")

            if (withdrawalResponse.isSuccessful) {
                emit(Result.Success(withdrawalResponse.body()?.data))
            } else {
                emit(Result.Error(withdrawalResponse.errorBody().toString()))
            }

        } catch (e: HttpException) {
            emit(Result.Error(e.message()))
        }
    }

    companion object {

        private const val TAG = "WithdrawalRepository"

        @Volatile
        private var INSTANCE: WithdrawalRepository? = null

        @JvmStatic
        fun getInstance(
            apiService: ApiService,
            authPreferences: AuthPreferences
        ): WithdrawalRepository {
            return INSTANCE ?: synchronized(this) {
                val instance = WithdrawalRepository(apiService, authPreferences)
                INSTANCE = instance
                instance
            }
        }
    }
}