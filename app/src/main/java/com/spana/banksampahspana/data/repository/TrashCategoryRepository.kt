package com.spana.banksampahspana.data.repository

import android.util.Log
import androidx.lifecycle.liveData
import com.spana.banksampahspana.data.Result
import com.spana.banksampahspana.data.local.AuthPreferences
import com.spana.banksampahspana.data.remote.response.TrashCategoryItem
import com.spana.banksampahspana.data.remote.retrofit.ApiService
import kotlinx.coroutines.flow.first
import retrofit2.HttpException

class TrashCategoryRepository private constructor(
    private val apiService: ApiService,
    private val authPreferences: AuthPreferences
) {

    fun getTrashCategories() = liveData {
        emit(Result.Loading)

        try {
            val response = apiService.getTrashCategory()

            if (response.isSuccessful) {
                emit(Result.Success(response.body()?.data))
            }

        } catch (e: HttpException) {
            emit(Result.Error(e.message()))
            Log.e(TAG, e.message())
        }
    }

    fun addTrashCategory(name: String, price: Int) = liveData {
        emit(Result.Loading)

        try {
            val token = authPreferences.getAuthToken().first()
            val trashCategoryResponse = apiService.addTrashCategory(
                "Bearer $token",
                name, price
            )

            if (trashCategoryResponse.isSuccessful) {
                emit(Result.Success(trashCategoryResponse.body()?.data))
            } else {
                emit(Result.Error(trashCategoryResponse.errorBody().toString()))
            }

        } catch (e: HttpException) {
            emit(Result.Error(e.message()))
        }
    }

    fun deleteTrashCategory(id: Int) = liveData {
        emit(Result.Loading)

        try {
            val token = authPreferences.getAuthToken().first()
            val trashCategoryResponse = apiService.deleteTrashCategory("Bearer $token", id)

            if (trashCategoryResponse.isSuccessful) {
                emit(Result.Success(trashCategoryResponse.body()))
            } else {
                emit(Result.Error(trashCategoryResponse.errorBody().toString()))
            }

        } catch (e: HttpException) {
            emit(Result.Error(e.message()))
        }
    }

    fun updateTrashCategory(trashCategoryItem: TrashCategoryItem) = liveData {
        emit(Result.Loading)

        try {
            val token = authPreferences.getAuthToken().first()
            val trashCategoryResponse = apiService.updateTrashCategory(
                "Bearer $token",
                trashCategoryItem.id ?: 0,
                trashCategoryItem.name.toString(),
                trashCategoryItem.price ?: 0
            )

            if (trashCategoryResponse.isSuccessful) {
                emit(Result.Success(trashCategoryResponse.body()))
            } else {
                emit(Result.Error(trashCategoryResponse.message()))
            }

        } catch (e: HttpException) {
            emit(Result.Error(e.message()))
        }
    }

    companion object {

        private const val TAG = "TrashCategoryRepository"

        @Volatile
        private var INSTANCE: TrashCategoryRepository? = null

        @JvmStatic
        fun getInstance(
            apiService: ApiService,
            authPreferences: AuthPreferences
        ): TrashCategoryRepository {
            return INSTANCE ?: synchronized(TrashCategoryRepository::class.java) {
                val instance = TrashCategoryRepository(apiService, authPreferences)
                INSTANCE = instance
                instance
            }
        }
    }
}