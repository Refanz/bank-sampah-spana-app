package com.spana.banksampahspana.data.repository

import android.util.Log
import androidx.lifecycle.liveData
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.spana.banksampahspana.data.Result
import com.spana.banksampahspana.data.local.AuthPreferences
import com.spana.banksampahspana.data.model.Admin
import com.spana.banksampahspana.data.model.User
import com.spana.banksampahspana.data.remote.response.ForgotPasswordErrorResponse
import com.spana.banksampahspana.data.remote.response.ForgotPasswordResponse
import com.spana.banksampahspana.data.remote.response.LoginErrorResponse
import com.spana.banksampahspana.data.remote.response.ResetPasswordErrorResponse
import com.spana.banksampahspana.data.remote.response.ResetPasswordResponse
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
            } else {
                val errorMessage =
                    Gson().fromJson(response.errorBody()?.string(), LoginErrorResponse::class.java)
                emit(Result.Error(errorMessage.message))
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

    fun registerAdmin(admin: Admin) = liveData {
        emit(Result.Loading)

        try {
            val response = apiService.registerAdmin(
                email = admin.email,
                password = admin.password,
                name = admin.name,
                nip = admin.nip,
                phone = admin.phone,
                gender = admin.gender
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

    fun updateUserAdmin(user: User) = liveData {
        emit(Result.Loading)

        try {
            val token = authPreferences.getAuthToken().first()

            val response = apiService.updateUserAdmin(
                id = user.id,
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

    fun downloadUserWithdrawalHistories() = liveData {
        emit(Result.Loading)

        try {
            val token = authPreferences.getAuthToken().first()
            val downloadResponse = apiService.downloadUserWithdrawalHistories("Bearer $token")

            emit(Result.Success(downloadResponse))

        } catch (e: HttpException) {
            emit(Result.Error(e.message()))
        }
    }

    fun deleteUser(id: Int) = liveData {
        emit(Result.Loading)

        try {
            val token = authPreferences.getAuthToken().first()
            val userResponse = apiService.deleteUser("Bearer $token", id)

            if (userResponse.isSuccessful) {
                emit(Result.Success(userResponse.body()))
            } else {
                emit(Result.Error(userResponse.message()))
            }

        } catch (e: HttpException) {
            emit(Result.Error(e.message()))
        }
    }

    fun updateAdminInfo(admin: Admin) = liveData {
        emit(Result.Loading)

        try {
            val token = authPreferences.getAuthToken().first()
            val response = apiService.updateAdminInfo(
                "Bearer $token",
                admin.name,
                admin.email,
                admin.nip,
                admin.gender,
                admin.phone
            )

            if (response.isSuccessful) {
                emit(Result.Success(response.body()))
            } else {
                emit(Result.Error(response.message()))
            }
        } catch (e: HttpException) {
            emit(Result.Error(e.message()))
        }
    }

    fun changePassword(currentPassword: String, newPassword: String) = liveData {
        emit(Result.Loading)

        try {
            val token = authPreferences.getAuthToken().first()
            val changePasswordResponse =
                apiService.changePassword("Bearer $token", currentPassword, newPassword)

            if (changePasswordResponse.isSuccessful) {
                emit(Result.Success(changePasswordResponse.body()?.message))
            } else {
                emit(Result.Error(changePasswordResponse.errorBody().toString()))
            }

        } catch (e: HttpException) {
            emit(Result.Error(e.message()))
        }
    }

    fun forgotPassword(email: String) = liveData {
        emit(Result.Loading)

        try {
            val forgotPasswordResponse = apiService.forgotPassword(email)

            if (forgotPasswordResponse.isSuccessful) {
                emit(Result.Success(forgotPasswordResponse.body()?.status))
            } else {
                val error =
                    JsonParser().parse(forgotPasswordResponse.errorBody()?.string()).asJsonObject

                if (error.has("status")) {
                    val errorMessage = Gson().fromJson(
                        error,
                        ForgotPasswordResponse::class.java
                    )
                    emit(Result.Error(errorMessage.status))
                } else if (error.has("message")) {
                    val errorMessage = Gson().fromJson(
                        error,
                        ForgotPasswordErrorResponse::class.java
                    )
                    emit(Result.Error(errorMessage.message))
                }
            }

        } catch (e: HttpException) {
            emit(Result.Error(e.message()))
        }
    }

    fun resetPassword(token: String, email: String, password: String, passwordConfirm: String) =
        liveData {
            emit(Result.Loading)

            try {
                val resetPasswordResponse =
                    apiService.resetPassword(token, email, password, passwordConfirm)

                if (resetPasswordResponse.isSuccessful) {
                    emit(Result.Success(resetPasswordResponse.body()?.status))
                } else {
                    val error =
                        JsonParser().parse(
                            resetPasswordResponse.errorBody()?.string()
                        ).asJsonObject

                    if (error.has("status")) {
                        val errorMessage = Gson().fromJson(
                            error,
                            ResetPasswordResponse::class.java
                        )
                        emit(Result.Error(errorMessage.status))
                    } else if (error.has("message")) {
                        val errorMessage = Gson().fromJson(
                            error,
                            ResetPasswordErrorResponse::class.java
                        )
                        emit(Result.Error(errorMessage.message))
                    }
                }
            } catch (e: HttpException) {
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