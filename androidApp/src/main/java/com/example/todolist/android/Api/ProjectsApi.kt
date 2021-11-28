package com.example.todolist.android.Api

import android.provider.ContactsContract
import com.example.todolist.android.models.Project
import com.example.todolist.android.models.ProjectExtended
import com.example.todolist.android.models.User
import retrofit2.Call
import retrofit2.http.*

interface ProjectsApi {

    @GET("projects")
    fun getProjects() : Call<List<ProjectExtended>>

    @GET("projects/{project_id}")
    fun getProject(
        @Path("project_id") projectId: Int,
    ) : Call<ProjectExtended>

    @POST("projects")
    fun addProject(
        @Body project: Project
    ) : Call<Boolean>

    @PUT("projects/{project_id}")
    fun updateProject(
        @Path("project_id") projectId: Int,
        @Body project: Project
    ) : Call<Boolean>

    @DELETE("projects/{project_id}")
    fun deleteProject(
        @Path("project_id") projectId: Int
    ) : Call<Boolean>

    @GET("projects/{project_id}/users")
    fun getUsers(
        @Path("project_id") projectId: Int
    ) : Call<List<User>>

    @POST("projects/{project_id}/users")
    fun addUser(
        @Path("project_id") projectId: Int,
        @Query("email") email: String
    ) : Call<Boolean>

    @DELETE("projects/{project_id}/users")
    fun deleteUser(
        @Path("project_id") projectId: Int,
        @Query("email") email: String
    ) : Call<Boolean>
}