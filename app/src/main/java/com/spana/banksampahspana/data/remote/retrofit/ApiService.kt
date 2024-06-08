package com.spana.banksampahspana.data.remote.retrofit

import com.spana.banksampahspana.data.remote.response.LoginResponse
import com.spana.banksampahspana.data.remote.response.RegisterResponse
import com.spana.banksampahspana.data.remote.response.TrashCategoryResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {

    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

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

    @GET("trash-categories")
    suspend fun getTrashCategory(): Response<TrashCategoryResponse>
}