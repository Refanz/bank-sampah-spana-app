package com.spana.banksampahspana.data.repository

import android.util.Log
import androidx.lifecycle.liveData
import com.google.gson.Gson
import com.spana.banksampahspana.data.Result
import com.spana.banksampahspana.data.model.User
import com.spana.banksampahspana.data.remote.response.Errors
import com.spana.banksampahspana.data.remote.response.Response
import com.spana.banksampahspana.data.remote.retrofit.ApiService
import retrofit2.HttpException

class AuthRepository private constructor(private val apiService: ApiService) {

    fun login(email: String, password: String) = liveData {
        emit(Result.Loading)

        try {
            val successResponse = apiService.login(email, password)
            emit(Result.Success(successResponse))
        } catch (e: HttpException) {
            Log.e(TAG, e.message())
            emit(Result.Error(e.message()))
        }
    }

    fun register(user: User) = liveData {
        emit(Result.Loading)

        try {
            val response = apiService.register(
                email = user.email,
                password = user.password,
                name = user.name,
                studentClass = user.studentClass,
                nis = user.nis,
                paymentMethod = user.paymentMethod,
                phone = user.phone,
                gender = user.gender
            )

            val result = response.body()

            if (response.isSuccessful) {
                emit(Result.Success(result))
                Log.d(TAG, result.toString())
            } else {
                val error = Gson().fromJson(response.errorBody()?.string(), Response::class.java)
                emit(Result.Error(error.errors.nis.toString()))
                Log.d(TAG, error.errors.toString())
            }
        } catch (e: Exception) {
            Log.e(TAG, e.message.toString())
            emit(Result.Error(e.message.toString()))
        }
    }

    companion object {
        private const val TAG = "AuthRepository"

        @Volatile
        private var INSTANCE: AuthRepository? = null

        @JvmStatic
        fun getInstance(apiService: ApiService): AuthRepository {
            if (INSTANCE == null) {
                synchronized(AuthRepository::class.java) {
                    INSTANCE = AuthRepository(apiService)
                }
            }

            return INSTANCE as AuthRepository
        }
    }
}