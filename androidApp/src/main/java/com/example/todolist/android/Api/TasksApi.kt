package com.example.todolist.android.Api

import com.example.todolist.android.models.Task
import com.example.todolist.android.models.TaskExtended
import retrofit2.Call
import retrofit2.http.*

interface TasksApi {

    @GET("tasks/{project_id}/{task_id}")
    fun getTask(
        @Path("project_id") projectId: Int,
        @Path("task_id") taskId: Int
    ) : Call<TaskExtended>

    @GET("tasks/{project_id}")
    fun getTasks(
        @Path("project_id") projectId: Int
    ) : Call<List<TaskExtended>>

    @POST("tasks/{project_id}/add")
    fun addTask(
        @Path("project_id") projectId: Int,
        @Body task: Task
    ): Call<Boolean>

    @DELETE("tasks/{project_id}/{task_id}")
    fun deleteTask(
        @Path("project_id") projectId: Int,
        @Path("task_id") taskId: Int
    ): Call<Boolean>

    @PUT("tasks/{project_id}/{task_id}")
    fun updateTask(
        @Path("project_id") projectId: Int,
        @Path("task_id") taskId: Int,
        @Body task: Task
    ) : Call<Boolean>
}