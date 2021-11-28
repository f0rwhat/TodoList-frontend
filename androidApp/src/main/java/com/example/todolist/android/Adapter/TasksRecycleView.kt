package com.example.todolist.android.Adapter

import android.app.PendingIntent
import android.app.PendingIntent.getActivity
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.android.R
import com.example.todolist.android.models.Task
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.appcompat.app.AppCompatActivity
import com.example.todolist.android.Fragments.TaskInfoFragment
import com.example.todolist.android.Fragments.TasksFragment
import com.example.todolist.android.models.TaskExtended

class TasksRecycleViewAdapter: RecyclerView.Adapter<TasksRecycleViewAdapter.TaskHolder>() {

    private var tasksList = emptyList<TaskExtended>()
    private var projectId: Int = 0
    private var projectTitle: String = ""
    inner class TaskHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    {
        var id: Int = -1
        val title: TextView = itemView.findViewById(R.id.titleText)
        var description: String = ""
        var priority: Int = -1
        val priorityImage: ImageView = itemView.findViewById(R.id.priorityBitMap)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskHolder {
        return TaskHolder(LayoutInflater.from(parent.context).inflate(R.layout.tasks_layout, parent, false))
    }

    override fun getItemCount(): Int {
        return tasksList.size
    }

    fun setProjectInfo(id: Int, title: String) {
        projectId = id
        projectTitle = title
    }

    override fun onBindViewHolder(holder: TaskHolder, position: Int) {
        when (tasksList[position].priority_id) {
            1 -> {
                holder.priorityImage.setBackgroundColor(Color.GREEN)
                holder.priorityImage.setColorFilter(Color.GREEN)
            }
            2 -> {
                holder.priorityImage.setBackgroundColor(Color.YELLOW)
                holder.priorityImage.setColorFilter(Color.YELLOW)
            }
            3 -> {
                holder.priorityImage.setBackgroundColor(Color.RED)
                holder.priorityImage.setColorFilter(Color.RED)
            }
        }

        holder.id = tasksList[position].id
        holder.title.text = tasksList[position].title
        holder.description = tasksList[position].description
        holder.priority = tasksList[position].priority_id
        holder.itemView.setOnClickListener{
            val tasksInfoFragment = TaskInfoFragment.newInstance(
                projectId, projectTitle, holder.id, holder.title.text.toString(), holder.description, holder.priority
            )
            val appCompatActivity = it.context as AppCompatActivity
            appCompatActivity.supportFragmentManager.
            beginTransaction()
                .replace(R.id.fl_wrapper, tasksInfoFragment)
                .addToBackStack(null)
                .commit()
        }
    }

    fun setData(newTasksList: List<TaskExtended>) {
        tasksList = newTasksList
        notifyDataSetChanged()
    }

}