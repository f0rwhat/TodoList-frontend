package com.example.todolist.android.Api

import android.content.Context
import com.example.todolist.android.utils.Constants.Companion.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiInstance  {

    private lateinit var tasksApi: TasksApi
    private lateinit var authorisationApi: AuthorisationApi
    private lateinit var projectsApi: ProjectsApi
    private lateinit var prioritiesApi: PriorityApi

    fun priorities(context: Context): PriorityApi {
        // Initialize ApiService if not initialized yet
        if (!::prioritiesApi.isInitialized) {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okhttpClient(context)) // Add our Okhttp client
                .build()

            prioritiesApi = retrofit.create(PriorityApi::class.java)
        }

        return prioritiesApi
    }

    fun projects(context: Context): ProjectsApi {
        // Initialize ApiService if not initialized yet
        if (!::projectsApi.isInitialized) {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okhttpClient(context)) // Add our Okhttp client
                .build()

            projectsApi = retrofit.create(ProjectsApi::class.java)
        }

        return projectsApi
    }

    fun tasks(context: Context): TasksApi {
        // Initialize ApiService if not initialized yet
        if (!::tasksApi.isInitialized) {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okhttpClient(context)) // Add our Okhttp client
                .build()

            tasksApi = retrofit.create(TasksApi::class.java)
        }

        return tasksApi
    }

    fun authorisation(context: Context): AuthorisationApi {
        // Initialize ApiService if not initialized yet
        if (!::authorisationApi.isInitialized) {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okhttpClient(context)) // Add our Okhttp client
                .build()

            authorisationApi = retrofit.create(AuthorisationApi::class.java)
        }

        return authorisationApi
    }

    /**
     * Initialize OkhttpClient with our interceptor
     */
    private fun okhttpClient(context: Context): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(context))
            .build()
    }
}