package com.spana.banksampahspana.data.remote.retrofit

import com.spana.banksampahspana.data.remote.response.LoginResponse
import com.spana.banksampahspana.data.remote.response.LogoutResponse
import com.spana.banksampahspana.data.remote.response.RegisterResponse
import com.spana.banksampahspana.data.remote.response.TotalWithdrawalResponse
import com.spana.banksampahspana.data.remote.response.TrashCategoryResponse
import com.spana.banksampahspana.data.remote.response.TrashResponse
import com.spana.banksampahspana.data.remote.response.UpdateUserInfoResponse
import com.spana.banksampahspana.data.remote.response.UserResponse
import com.spana.banksampahspana.data.remote.response.UserTrashResponse
import com.spana.banksampahspana.data.remote.response.WithdrawalHistoryResponse
import com.spana.banksampahspana.data.remote.response.WithdrawalResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT

interface ApiService {


    /* User */

    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<LoginResponse>

    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("register")
    suspend fun register(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("name") name: String,
        @Field("nis") nis: String,
        @Field("class") studentClass: Int,
        @Field("gender") gender: String,
        @Field("payment_method") paymentMethod: String,
        @Field("phone") phone: String
    ): Response<RegisterResponse>

    @Headers("Accept: application/json")
    @POST("logout")
    suspend fun logout(@Header("Authorization") authorization: String): Response<LogoutResponse>

    @Headers("Accept: application/json")
    @GET("user")
    suspend fun getUserInfo(@Header("Authorization") authorization: String): Response<UserResponse>

    @FormUrlEncoded
    @Headers("Accept: application/json")
    @PUT("user")
    suspend fun updateUserInfo(
        @Header("Authorization") authorization: String,
        @Field("email") email: String,
        @Field("name") name: String,
        @Field("nis") nis: String,
        @Field("class") studentClass: Int,
        @Field("gender") gender: String,
        @Field("payment_method") paymentMethod: String,
        @Field("phone") phone: String
    ): Response<UpdateUserInfoResponse>

    /* Trash */

    @GET("trash-categories")
    suspend fun getTrashCategory(): Response<TrashCategoryResponse>

    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("trashes")
    suspend fun addNewTrash(
        @Field("trash_type") trashType: String,
        @Field("weight") weight: Double,
        @Field("total_deposit") totalDeposit: Int,
        @Field("user_id") userId: Int,
        @Header("Authorization") authorization: String
    ): Response<TrashResponse>

    @Headers("Accept: application/json", "Content-Type: application/json")
    @GET("trashes/user")
    suspend fun getUserTrash(@Header("Authorization") authorization: String): Response<UserTrashResponse>

    /* Withdrawal */

    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("withdrawal-history")
    suspend fun withdrawal(
        @Header("Authorization") authorization: String,
        @Field("total_balance") totalBalance: Int
    ): Response<WithdrawalResponse>

    @Headers("Accept: application/json")
    @GET("withdrawal/total-withdrawal")
    suspend fun getUserTotalWithdrawal(
        @Header("Authorization") authorization: String
    ): Response<TotalWithdrawalResponse>

    @Headers("Accept: application/json")
    @GET("withdrawal-history/user")
    suspend fun getUserWithdrawalHistories(
        @Header("Authorization") authorization: String
    ): Response<WithdrawalHistoryResponse>
}