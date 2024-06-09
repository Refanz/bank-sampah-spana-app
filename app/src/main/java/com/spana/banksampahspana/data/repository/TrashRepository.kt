package com.spana.banksampahspana.data.repository

import androidx.lifecycle.liveData
import com.spana.banksampahspana.data.Result
import com.spana.banksampahspana.data.remote.response.Trash
import com.spana.banksampahspana.data.remote.retrofit.ApiService
import retrofit2.HttpException

class TrashRepository private constructor(private val apiService: ApiService) {

    fun addNewTrash(trash: Trash) = liveData {
        emit(Result.Loading)

        try {
            val response = apiService.addNewTrash(
                trash.trashType,
                trash.weight,
                trash.totalDeposit,
                trash.id
            )

            if (response.isSuccessful) {
                emit(Result.Success(response.body()?.trash))
            } else {
                emit(Result.Error(response.errorBody().toString()))
            }

        } catch (e: HttpException) {
            emit(Result.Error(e.message()))
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: TrashRepository? = null

        @JvmStatic
        fun getInstance(apiService: ApiService): TrashRepository {
            return INSTANCE ?: synchronized(this) {
                val instance = TrashRepository(apiService)
                INSTANCE = instance
                instance
            }
        }
    }
}