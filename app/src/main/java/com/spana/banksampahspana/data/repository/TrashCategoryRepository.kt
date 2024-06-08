package com.spana.banksampahspana.data.repository

import android.util.Log
import androidx.lifecycle.liveData
import com.spana.banksampahspana.data.Result
import com.spana.banksampahspana.data.remote.retrofit.ApiService
import retrofit2.HttpException

class TrashCategoryRepository private constructor(private val apiService: ApiService) {

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


    companion object {

        private const val TAG = "TrashCategoryRepository"

        @Volatile
        private var INSTANCE: TrashCategoryRepository? = null

        @JvmStatic
        fun getInstance(apiService: ApiService): TrashCategoryRepository {
            return INSTANCE ?: synchronized(TrashCategoryRepository::class.java) {
                val instance = TrashCategoryRepository(apiService)
                INSTANCE = instance
                instance
            }
        }
    }
}