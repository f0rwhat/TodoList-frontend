package com.example.todolist.android.Adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.android.Fragments.TasksFragment
import com.example.todolist.android.R
import com.example.todolist.android.models.ProjectExtended

class ProjectsRecycleViewAdapter: RecyclerView.Adapter<ProjectsRecycleViewAdapter.ProjectHolder>() {

    private var projectsList = emptyList<ProjectExtended>()

    inner class ProjectHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    {
        val title: TextView = itemView.findViewById(R.id.project_title_view)
        var maintainer: TextView = itemView.findViewById(R.id.maintainer_name_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectHolder {
        return ProjectHolder(LayoutInflater.from(parent.context).inflate(R.layout.projects_layout, parent, false))
    }

    override fun getItemCount(): Int {
        return projectsList.size
    }

    override fun onBindViewHolder(holder: ProjectHolder, position: Int) {
        holder.title.text = projectsList[position].title
        holder.maintainer.text = projectsList[position].maintainer_name
        holder.itemView.setOnClickListener{
            val currentProjectId = projectsList[position].id
            val tasksFragment = TasksFragment.newInstance(
                projectsList[position].id, projectsList[position].title, projectsList[position].description, projectsList[position].maintainer_name
            )
            val appCompatActivity = it.context as AppCompatActivity
            appCompatActivity.supportFragmentManager.
            beginTransaction()
                .replace(R.id.fl_wrapper, tasksFragment)
                .addToBackStack(null)
                .commit()
        }
    }

    fun setData(newProjectsList: List<ProjectExtended>) {
        projectsList = newProjectsList
        Log.d("UPDATING PROJECTS", projectsList.size.toString())
        notifyDataSetChanged()
    }

}