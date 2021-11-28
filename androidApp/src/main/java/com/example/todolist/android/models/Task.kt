package com.example.todolist.android.models

data class Task(
    var title: String,
    val description: String,
    val priority_id: Int
)

data class TaskExtended (
    val id: Int,
    val project_id: Int,
    var title: String,
    val description: String,
    val priority_id: Int
)
