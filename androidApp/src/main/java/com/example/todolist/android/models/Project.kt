package com.example.todolist.android.models

data class Project(
    val title: String,
    val description: String
)

data class ProjectExtended(
    val id: Int,
    val title: String,
    val description: String,
    val maintainer_name: String
)
