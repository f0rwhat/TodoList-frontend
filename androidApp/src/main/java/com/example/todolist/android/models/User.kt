package com.example.todolist.android.models

import android.provider.ContactsContract
import com.google.gson.annotations.SerializedName

data class User(
    val name: String,
    val email: String,
)

data class UserWithToken(
    val user: User,
    val access_token: String
)

data class AccessToken(
    val access_token: String
)

data class RegisterUser(
    val name: String,
    val email: String,
    val password: String,
    val password2: String,
)

data class LoginUser(
    @SerializedName("username")
    var email: String,

    @SerializedName("password")
    var password: String
)
