package com.example.todolist.android.Api

import com.example.todolist.android.models.Priority
import retrofit2.Call
import retrofit2.http.GET

interface PriorityApi {

    @GET("priorities")
    fun getPriorities() : Call<List<Priority>>

}