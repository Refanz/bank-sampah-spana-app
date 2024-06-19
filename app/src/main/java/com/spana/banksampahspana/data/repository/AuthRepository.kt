package com.spana.banksampahspana.data.repository

import android.util.Log
import androidx.lifecycle.liveData
import com.google.gson.Gson
import com.spana.banksampahspana.data.Result
import com.spana.banksampahspana.data.local.AuthPreferences
import com.spana.banksampahspana.data.model.User
import com.spana.banksampahspana.data.remote.response.Response
import com.spana.banksampahspana.data.remote.retrofit.ApiService
import kotlinx.coroutines.flow.first
import retrofit2.HttpException

class AuthRepository private constructor(
    private val apiService: ApiService,
    private val authPreferences: AuthPreferences
) {

    fun login(email: String, password: String) = liveData {
        emit(Result.Loading)

        try {
            val response = apiService.login(email, password)

            if (response.isSuccessful) {

                val userResult = response.body()?.user

                val user = User(
                    id = userResult?.id ?: 0,
                    name = userResult?.name ?: "",
                    email = userResult?.email ?: "",
                    role = userResult?.role ?: ""
                )

                authPreferences.saveAuthUser(user)
                authPreferences.saveAuthToken(response.body()?.accessToken ?: "")

                emit(Result.Success(response.body()?.message))
            }


        } catch (e: Exception) {
            Log.e(TAG, e.message.toString())
            emit(Result.Error(e.message.toString()))
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

    fun getUserInfo() = liveData {
        emit(Result.Loading)

        try {
            val token = authPreferences.getAuthToken().first()

            val response = apiService.getUserInfo("Bearer $token")

            if (response.isSuccessful) {
                emit(Result.Success(response.body()))
            } else {
                emit(Result.Error(response.errorBody().toString()))
            }

        } catch (e: HttpException) {
            Log.e(TAG, e.message())
            emit(Result.Error(e.message()))
        }
    }

    fun userLogout() = liveData {
        emit(Result.Loading)

        try {
            val token = authPreferences.getAuthToken().first()
            val response = apiService.userLogout("Bearer $token")

            if (response.isSuccessful) {
                authPreferences.removeAuthUser()
                emit(Result.Success(response.body()))
                Log.d(TAG, response.body().toString())
            } else {
                emit(Result.Error(response.errorBody().toString()))
                Log.d(TAG, response.body().toString())
            }

        } catch (e: HttpException) {
            Log.e(TAG, e.message())
            emit(Result.Error(e.message()))
        }

    }

    fun updateUserInfo(user: User) = liveData {
        emit(Result.Loading)

        try {
            val token = authPreferences.getAuthToken().first()

            val response = apiService.updateUserInfo(
                name = user.name,
                email = user.email,
                nis = user.nis,
                phone = user.phone,
                gender = user.gender,
                paymentMethod = user.paymentMethod,
                studentClass = user.studentClass,
                authorization = "Bearer $token"
            )

            if (response.isSuccessful) {
                emit(Result.Success(response.body()))
            } else {
                emit(Result.Error(response.errorBody().toString()))
            }

        } catch (e: HttpException) {
            Log.e(TAG, e.message())
            emit(Result.Error(e.message()))
        }
    }

    fun getUsers() = liveData {
        emit(Result.Loading)

        try {
            val token = authPreferences.getAuthToken().first()
            val userResponse = apiService.getUsers("Bearer $token")

            if (userResponse.isSuccessful) {
                emit(Result.Success(userResponse.body()?.data))
            } else {
                emit(Result.Error(userResponse.errorBody().toString()))
            }

        } catch (e: HttpException) {
            emit(Result.Error(e.message()))
        }

    }

    fun getAdminInfo() = liveData {
        emit(Result.Loading)

        try {
            val token = authPreferences.getAuthToken().first()
            val adminResponse = apiService.getAdminInfo("Bearer $token")

            if (adminResponse.isSuccessful) {
                emit(Result.Success(adminResponse.body()))
            } else {
                emit(Result.Error(adminResponse.errorBody().toString()))
            }

        } catch (e: HttpException) {
            emit(Result.Error(e.message()))
        }
    }

    fun adminLogout() = liveData {
        emit(Result.Loading)

        try {

            val token = authPreferences.getAuthToken().first()
            val logoutResponse = apiService.adminLogout("Bearer $token")

            if (logoutResponse.isSuccessful) {
                authPreferences.removeAuthUser()
                emit(Result.Success(logoutResponse.body()))
            } else {
                emit(Result.Error(logoutResponse.errorBody().toString()))
            }

        } catch (e: HttpException) {
            Log.e(TAG, e.message())
            emit(Result.Error(e.message()))
        }
    }

    companion object {
        private const val TAG = "AuthRepository"

        @Volatile
        private var INSTANCE: AuthRepository? = null

        @JvmStatic
        fun getInstance(apiService: ApiService, authPreferences: AuthPreferences): AuthRepository {
            if (INSTANCE == null) {
                synchronized(AuthRepository::class.java) {
                    INSTANCE = AuthRepository(apiService, authPreferences)
                }
            }

            return INSTANCE as AuthRepository
        }
    }
}