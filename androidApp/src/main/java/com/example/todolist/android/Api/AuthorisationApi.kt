package com.example.todolist.android.Api

import com.example.todolist.android.models.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface AuthorisationApi {

    @POST("user/register")
    fun register(
        @Body user: RegisterUser
    ): Call<UserWithToken>

    @POST("user/login")
    @FormUrlEncoded
    fun login(
        @Field("username") username: String,
        @Field("password") password: String,
    ): Call<AccessToken>

    @GET("user")
    fun user(): Call<User>
}