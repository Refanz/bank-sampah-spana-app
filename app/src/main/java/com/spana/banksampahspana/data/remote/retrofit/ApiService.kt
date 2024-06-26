package com.spana.banksampahspana.data.remote.retrofit

import com.spana.banksampahspana.data.remote.response.AdminResponse
import com.spana.banksampahspana.data.remote.response.ChangePasswordResponse
import com.spana.banksampahspana.data.remote.response.DeleteUserResponse
import com.spana.banksampahspana.data.remote.response.LoginResponse
import com.spana.banksampahspana.data.remote.response.LogoutResponse
import com.spana.banksampahspana.data.remote.response.NotificationResponse
import com.spana.banksampahspana.data.remote.response.RegisterResponse
import com.spana.banksampahspana.data.remote.response.TokenResponse
import com.spana.banksampahspana.data.remote.response.TotalWithdrawalResponse
import com.spana.banksampahspana.data.remote.response.TrashCategoryActionResponse
import com.spana.banksampahspana.data.remote.response.TrashCategoryItemResponse
import com.spana.banksampahspana.data.remote.response.TrashCategoryResponse
import com.spana.banksampahspana.data.remote.response.TrashResponse
import com.spana.banksampahspana.data.remote.response.UpdateAdminInfoResponse
import com.spana.banksampahspana.data.remote.response.UpdateUserInfoResponse
import com.spana.banksampahspana.data.remote.response.UpdateWithdrawalAdminResponse
import com.spana.banksampahspana.data.remote.response.UserResponse
import com.spana.banksampahspana.data.remote.response.UserTrashResponse
import com.spana.banksampahspana.data.remote.response.UsersResponse
import com.spana.banksampahspana.data.remote.response.WithdrawalAdminResponse
import com.spana.banksampahspana.data.remote.response.WithdrawalHistoryResponse
import com.spana.banksampahspana.data.remote.response.WithdrawalResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Streaming

interface ApiService {

    /* Admin */
    @Headers("Accept: application/json")
    @GET("admin")
    suspend fun getAdminInfo(@Header("Authorization") authorization: String): Response<AdminResponse>

    @FormUrlEncoded
    @Headers("Accept: application/json")
    @PUT("admin")

    suspend fun updateAdminInfo(
        @Header("Authorization") authorization: String,
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("nip") nip: String,
        @Field("gender") gender: String,
        @Field("phone") phone: String
    ): Response<UpdateAdminInfoResponse>

    @Headers("Accept: application/json")
    @GET("admin/users")
    suspend fun getUsers(@Header("Authorization") authorization: String): Response<UsersResponse>

    @Headers("Accept: application/json")
    @POST("admin/logout")
    suspend fun adminLogout(@Header("Authorization") authorization: String): Response<LogoutResponse>

    @Streaming
    @GET("admin/download/transaction")
    suspend fun downloadUserWithdrawalHistories(@Header("Authorization") authorization: String): ResponseBody

    @Headers("Accept: application/json")
    @GET("admin/withdrawal-histories/{status}")
    suspend fun getUserWithdrawalHistoriesByStatus(
        @Header("Authorization") authorization: String,
        @Path("status") status: String
    ): Response<WithdrawalAdminResponse>

    @FormUrlEncoded
    @Headers("Accept: application/json")
    @PUT("admin/withdrawal-history/{id}")
    suspend fun updateUserWithdrawalStatus(
        @Header("Authorization") authorization: String,
        @Path("id") id: Int,
        @Field("status") status: String
    ): Response<UpdateWithdrawalAdminResponse>

    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("admin/trash")
    suspend fun addUserTrashAdmin(
        @Header("Authorization") authorization: String,
        @Field("trash_type") trashType: String,
        @Field("weight") weight: Double,
        @Field("total_deposit") totalDeposit: Int,
        @Field("nis") nis: String,
    ): Response<TrashResponse>

    @FormUrlEncoded
    @Headers("Accept: application/json")
    @PUT("admin/users/{id}")
    suspend fun updateUserAdmin(
        @Header("Authorization") authorization: String,
        @Path("id") id: Int,
        @Field("email") email: String,
        @Field("name") name: String,
        @Field("nis") nis: String,
        @Field("class") studentClass: Int,
        @Field("gender") gender: String,
        @Field("payment_method") paymentMethod: String,
        @Field("phone") phone: String
    ): Response<UpdateUserInfoResponse>

    @Headers("Accept: application/json")
    @DELETE("admin/users/{id}")
    suspend fun deleteUser(
        @Header("authorization") authorization: String,
        @Path("id") id: Int,
    ): Response<DeleteUserResponse>

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
    @POST("user/logout")
    suspend fun userLogout(@Header("Authorization") authorization: String): Response<LogoutResponse>

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

    @FormUrlEncoded
    @Headers("Accept: application/json")
    @PUT("change-password")
    suspend fun changePassword(
        @Header("Authorization") authorization: String,
        @Field("current_password") currentPassword: String,
        @Field("new_password") newPassword: String
    ): Response<ChangePasswordResponse>

    /* Trash Categories */
    @GET("trash-categories")
    suspend fun getTrashCategory(): Response<TrashCategoryResponse>

    @GET("admin/trash-categories/{id}")
    suspend fun getTrashCategoryDetail(
        @Header("authorization") authorization: String,
        @Path("id") id: Int
    ): Response<TrashCategoryItemResponse>

    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("admin/trash-categories")
    suspend fun addTrashCategory(
        @Header("authorization") authorization: String,
        @Field("name") trashCategoryName: String,
        @Field("price") trashCategoryPrice: Int
    ): Response<TrashCategoryItemResponse>

    @FormUrlEncoded
    @Headers("Accept: application/json")
    @PUT("admin/trash-categories/{id}")
    suspend fun updateTrashCategory(
        @Header("authorization") authorization: String,
        @Path("id") id: Int,
        @Field("name") trashCategoryName: String,
        @Field("price") trashCategoryPrice: Int
    ): Response<TrashCategoryActionResponse>

    @Headers("Accept: application/json")
    @DELETE("admin/trash-categories/{id}")
    suspend fun deleteTrashCategory(
        @Header("authorization") authorization: String,
        @Path("id") id: Int,
    ): Response<TrashCategoryActionResponse>


    /* Trash */
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

    // Notification
    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("admin/notif")
    suspend fun sendNotification(
        @Field("id") id: Int,
        @Field("title") title: String,
        @Field("body") body: String
    ): Response<NotificationResponse>

    @FormUrlEncoded
    @Headers("Accept: application/json")
    @PUT("admin/token")
    suspend fun updateToken(
        @Field("id") id: Int,
        @Field("fcm_token") fcmToken: String,
    ): Response<TokenResponse>
}