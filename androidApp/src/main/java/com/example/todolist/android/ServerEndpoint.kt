package com.example.todolist.android

import android.R
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import com.example.todolist.android.Activities.MainActivity
import com.example.todolist.android.Api.ApiInstance
import com.example.todolist.android.models.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import org.json.JSONObject




object ServerEndpoint {
    private val connectionErrorMsg: String = "Couldn't connect to the server"
    fun getProjects(context: Context, successCallback: (List<ProjectExtended>) -> Unit, errorCallback: (msg: String) -> Unit) {
        ApiInstance.projects(context).getProjects()
            .enqueue(object : Callback<List<ProjectExtended>> {
                override fun onFailure(call: Call<List<ProjectExtended>>, t: Throwable) {
                    errorCallback(connectionErrorMsg)
                }

                override fun onResponse(call: Call<List<ProjectExtended>>, response: Response<List<ProjectExtended>>) {
                    if (response.isSuccessful) {
                        successCallback(response.body()!!)
                    } else {
                        val jObjError = JSONObject(response.errorBody()!!.string())
                        if (jObjError.has("msg")) {
                            errorCallback(jObjError["msg"].toString())
                        } else {
                            errorCallback("Unknown server error occurred")
                        }
                    }
                }
            })
    }
    fun getProject(context: Context, projectId: Int, successCallback: (ProjectExtended) -> Unit, errorCallback: (msg: String) -> Unit) {
        ApiInstance.projects(context).getProject(projectId)
            .enqueue(object : Callback<ProjectExtended> {
                override fun onResponse(call: Call<ProjectExtended>, response: Response<ProjectExtended>) {
                    if (response.isSuccessful) {
                        successCallback(response.body()!!)
                    } else {
                        val jObjError = JSONObject(response.errorBody()!!.string())
                        if (jObjError.has("msg")) {
                            errorCallback(jObjError["msg"].toString())
                        } else {
                            errorCallback("Unknown server error occurred")
                        }
                    }
                }

                override fun onFailure(call: Call<ProjectExtended>, t: Throwable) {
                    errorCallback(connectionErrorMsg)
                }
            })
    }

    fun getProjectUsers(context: Context, projectId: Int, successCallback: (List<User>) -> Unit, errorCallback: (msg: String) -> Unit) {
        ApiInstance.projects(context).getUsers(projectId!!)
            .enqueue(object : Callback<List<User>> {
                override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                    if (response.isSuccessful) {
                        successCallback(response.body()!!)
                    } else {
                        val jObjError = JSONObject(response.errorBody()!!.string())
                        if (jObjError.has("msg")) {
                            errorCallback(jObjError["msg"].toString())
                        } else {
                            errorCallback("Unknown server error occurred")
                        }
                    }
                }

                override fun onFailure(call: Call<List<User>>, t: Throwable) {
                    errorCallback(connectionErrorMsg)
                }

            })
    }

    fun createProject(context: Context, project: Project, successCallback: (Boolean) -> Unit, errorCallback: (msg: String) -> Unit) {
        ApiInstance.projects(context).addProject(project)
            .enqueue(object : Callback<Boolean> {
                override fun onFailure(call: Call<Boolean>, t: Throwable) {
                    errorCallback(connectionErrorMsg)
                }

                override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                    if (response.isSuccessful) {
                        successCallback(response.body()!!)
                    } else {
                        val jObjError = JSONObject(response.errorBody()!!.string())
                        if (jObjError.has("msg")) {
                            errorCallback(jObjError["msg"].toString())
                        } else {
                            errorCallback("Unknown server error occurred")
                        }
                    }
                }
            })
    }

    fun updateProject(context: Context, projectId: Int, project: Project, successCallback: (Boolean) -> Unit, errorCallback: (msg: String) -> Unit) {
        ApiInstance.projects(context).updateProject(projectId, project)
            .enqueue(object : Callback<Boolean> {
                override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                    if (response.isSuccessful) {
                        successCallback(response.body()!!)
                    } else {
                        val jObjError = JSONObject(response.errorBody()!!.string())
                        if (jObjError.has("msg")) {
                            errorCallback(jObjError["msg"].toString())
                        } else {
                            errorCallback("Unknown server error occurred")
                        }
                    }
                }

                override fun onFailure(call: Call<Boolean>, t: Throwable) {
                    errorCallback(connectionErrorMsg)
                }
            })
    }

    fun deleteProject(context: Context, projectId: Int, successCallback: (Boolean) -> Unit, errorCallback: (msg: String) -> Unit) {
        ApiInstance.projects(context).deleteProject(projectId)
            .enqueue(object : Callback<Boolean> {
                override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                    if (response.isSuccessful) {
                        successCallback(response.body()!!)
                    } else {
                        val jObjError = JSONObject(response.errorBody()!!.string())
                        if (jObjError.has("msg")) {
                            errorCallback(jObjError["msg"].toString())
                        } else {
                            errorCallback("Unknown server error occurred")
                        }
                    }
                }

                override fun onFailure(call: Call<Boolean>, t: Throwable) {
                    errorCallback(connectionErrorMsg)
                }
            })
    }

    fun addUserToProject(context: Context, projectId: Int, email: String, successCallback: (Boolean) -> Unit, errorCallback: (msg: String) -> Unit) {
        ApiInstance.projects(context).addUser(projectId, email)
            .enqueue(object : Callback<Boolean> {
                override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                    if (response.isSuccessful) {
                        successCallback(response.body()!!)
                    } else {
                        val jObjError = JSONObject(response.errorBody()!!.string())
                        if (jObjError.has("msg")) {
                            errorCallback(jObjError["msg"].toString())
                        } else {
                            errorCallback("Unknown server error occurred")
                        }
                    }
                }

                override fun onFailure(call: Call<Boolean>, t: Throwable) {
                    errorCallback(connectionErrorMsg)
                }

            })
    }

    fun deleteUserFromProject(context: Context, projectId: Int, email: String, successCallback: (Boolean) -> Unit, errorCallback: (msg: String) -> Unit) {
        ApiInstance.projects(context).deleteUser(projectId, email)
            .enqueue(object : Callback<Boolean> {
                override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                    if (response.isSuccessful) {
                        successCallback(response.body()!!)
                    } else {
                        val jObjError = JSONObject(response.errorBody()!!.string())
                        if (jObjError.has("msg")) {
                            errorCallback(jObjError["msg"].toString())
                        } else {
                            errorCallback("Unknown server error occurred")
                        }
                    }
                }

                override fun onFailure(call: Call<Boolean>, t: Throwable) {
                    errorCallback(connectionErrorMsg)
                }

            })
    }

    fun getCurrentUser(context: Context, successCallback: (User) -> Unit, errorCallback: (msg: String) -> Unit) {
        ApiInstance.authorisation(context).user()
            .enqueue(object : Callback<User> {
                override fun onFailure(call: Call<User>, t: Throwable) {
                    errorCallback(connectionErrorMsg)
                }

                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if (response.isSuccessful) {
                        successCallback(response.body()!!)
                    } else {
                        val jObjError = JSONObject(response.errorBody()!!.string())
                        if (jObjError.has("msg")) {
                            errorCallback(jObjError["msg"].toString())
                        } else {
                            errorCallback("Unknown server error occurred")
                        }
                    }
                }
            })
    }

    fun register(context: Context, user: RegisterUser, successCallback: (UserWithToken) -> Unit, errorCallback: (msg: String) -> Unit) {
        ApiInstance.authorisation(context).register(user)
            .enqueue(object : Callback<UserWithToken> {
                override fun onFailure(call: Call<UserWithToken>, t: Throwable) {
                    errorCallback(connectionErrorMsg)
                }

                override fun onResponse(call: Call<UserWithToken>, response: Response<UserWithToken>) {
                    if (response.isSuccessful) {
                        successCallback(response.body()!!)
                    } else {
                        val jObjError = JSONObject(response.errorBody()!!.string())
                        if (jObjError.has("msg")) {
                            errorCallback(jObjError["msg"].toString())
                        } else {
                            errorCallback("Unknown server error occurred")
                        }
                    }
                }
            })
    }

    fun login(context: Context, email: String, password: String, successCallback: (AccessToken) -> Unit, errorCallback: (msg: String) -> Unit) {
        ApiInstance.authorisation(context).login(email, password)
            .enqueue(object : Callback<AccessToken> {
                override fun onFailure(call: Call<AccessToken>, t: Throwable) {
                    errorCallback(connectionErrorMsg)
                }

                override fun onResponse(call: Call<AccessToken>, response: Response<AccessToken>) {
                    if (response.isSuccessful) {
                        successCallback(response.body()!!)
                    } else {
                        val jObjError = JSONObject(response.errorBody()!!.string())
                        if (jObjError.has("msg")) {
                            errorCallback(jObjError["msg"].toString())
                        } else {
                            errorCallback("Unknown server error occurred")
                        }
                    }
                }
            })
    }

    fun getTasksOfProject(context: Context, projectId: Int, successCallback: (List<TaskExtended>) -> Unit, errorCallback: (msg: String) -> Unit) {
        ApiInstance.tasks(context).getTasks(projectId)
            .enqueue(object : Callback<List<TaskExtended>> {
                override fun onFailure(call: Call<List<TaskExtended>>, t: Throwable) {
                    errorCallback(connectionErrorMsg)
                }

                override fun onResponse(call: Call<List<TaskExtended>>, response: Response<List<TaskExtended>>) {
                    if (response.isSuccessful) {
                        successCallback(response.body()!!)
                    } else {
                        val jObjError = JSONObject(response.errorBody()!!.string())
                        if (jObjError.has("msg")) {
                            errorCallback(jObjError["msg"].toString())
                        } else {
                            errorCallback("Unknown server error occurred")
                        }
                    }
                }
            })
    }

    fun addTaskToProject(context: Context, projectId: Int, task: Task, successCallback: (Boolean) -> Unit, errorCallback: (msg: String) -> Unit) {
        ApiInstance.tasks(context).addTask(projectId, task)
            .enqueue(object : Callback<Boolean>{
                override fun onFailure(call: Call<Boolean>, t: Throwable) {
                    errorCallback(connectionErrorMsg)
                }

                override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                    if (response.isSuccessful) {
                        successCallback(response.body()!!)
                    } else {
                        val jObjError = JSONObject(response.errorBody()!!.string())
                        if (jObjError.has("msg")) {
                            errorCallback(jObjError["msg"].toString())
                        } else {
                            errorCallback("Unknown server error occurred")
                        }
                    }
                }
            })
    }

    fun updateTask(context: Context, projectId: Int, taskId: Int, task: Task, successCallback: (Boolean) -> Unit, errorCallback: (msg: String) -> Unit) {
        ApiInstance.tasks(context).updateTask(projectId, taskId, task)
            .enqueue(object : Callback<Boolean>{
                override fun onFailure(call: Call<Boolean>, t: Throwable) {
                    errorCallback(connectionErrorMsg)
                }

                override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                    if (response.isSuccessful) {
                        successCallback(response.body()!!)
                    } else {
                        val jObjError = JSONObject(response.errorBody()!!.string())
                        if (jObjError.has("msg")) {
                            errorCallback(jObjError["msg"].toString())
                        } else {
                            errorCallback("Unknown server error occurred")
                        }
                    }
                }
            })
    }

    fun deleteTaskFromProject(context: Context, projectId: Int, taskId: Int, successCallback: (Boolean) -> Unit, errorCallback: (msg: String) -> Unit) {
        ApiInstance.tasks(context).deleteTask(projectId, taskId)
            .enqueue(object : Callback<Boolean>{
                override fun onFailure(call: Call<Boolean>, t: Throwable) {
                    errorCallback(connectionErrorMsg)
                }

                override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                    if (response.isSuccessful) {
                        successCallback(response.body()!!)
                    } else {
                        val jObjError = JSONObject(response.errorBody()!!.string())
                        if (jObjError.has("msg")) {
                            errorCallback(jObjError["msg"].toString())
                        } else {
                            errorCallback("Unknown server error occurred")
                        }
                    }
                }
            })
    }

    fun getPriorities(context: Context, successCallback: (List<Priority>) -> Unit, errorCallback: (msg: String) -> Unit) {
        ApiInstance.priorities(context).getPriorities()
            .enqueue(object : Callback<List<Priority>> {
                override fun onResponse(call: Call<List<Priority>>, response: Response<List<Priority>>) {
                    if (response.isSuccessful) {
                        successCallback(response.body()!!)
                    } else {
                        val jObjError = JSONObject(response.errorBody()!!.string())
                        if (jObjError.has("msg")) {
                            errorCallback(jObjError["msg"].toString())
                        } else {
                            errorCallback("Unknown server error occurred")
                        }
                    }
                }

                override fun onFailure(call: Call<List<Priority>>, t: Throwable) {
                    errorCallback(connectionErrorMsg)
                }

            })
    }
}