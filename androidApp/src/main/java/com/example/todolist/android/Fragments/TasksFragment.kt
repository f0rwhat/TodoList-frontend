package com.example.todolist.android.Fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.android.Adapter.TasksRecycleViewAdapter
import com.example.todolist.android.R
import com.example.todolist.android.models.Task
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.todolist.android.ServerEndpoint
import com.example.todolist.android.models.ProjectExtended

private const val ARG_PARAM1 = "project_id"
private const val ARG_PARAM2 = "project_name"
private const val ARG_PARAM3 = "project_description"
private const val ARG_PARAM4 = "project_maintainer"

class TasksFragment : Fragment() {

    private val tasksRecycleViewAdapter by lazy { TasksRecycleViewAdapter() }
    private lateinit var project: ProjectExtended
    private lateinit var tasksRecycleView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        arguments?.let {
            project = ProjectExtended(
                it.getInt(ARG_PARAM1), it.getString(ARG_PARAM2)!!, it.getString(ARG_PARAM3)!!, it.getString(ARG_PARAM4)!!
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v: View = inflater.inflate(R.layout.fragment_tasks, container, false)

        val projectTitleView : TextView = v.findViewById(R.id.project_title_label)
        val projectDescriptionView : TextView = v.findViewById(R.id.project_description_label)
        projectTitleView.text = project.title
        projectDescriptionView.text = project.description

        tasksRecycleViewAdapter.setProjectInfo(project.id, project.title)
        tasksRecycleView = v.findViewById(R.id.tasks_recycle_view)
        tasksRecycleView.adapter = tasksRecycleViewAdapter
        tasksRecycleView.layoutManager = LinearLayoutManager(activity)

        ServerEndpoint.getProject(v.context, project.id, {
            if (project != it) {
                project = it
                projectTitleView.text = project.title
                projectDescriptionView.text = project.description
            }
        }, {
            Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
        })

        ServerEndpoint.getTasksOfProject(v.context, project.id, {
            tasksRecycleViewAdapter.setData(it)
        }, {
            Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
        })

        val addTaskBtn : Button = v.findViewById(R.id.add_task_button)
        addTaskBtn.setOnClickListener {
            val fragment: TaskCreationFragment = TaskCreationFragment.newInstance(
                project.title, "", "", 0
            )
            fragment.setConfirmCallback { task -> createTask(v.context, task) }
            val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fl_wrapper, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        val editProjectBtn : Button = v.findViewById(R.id.project_settings_button)
        editProjectBtn.setOnClickListener {
            val fragment: ProjectEditFragment = ProjectEditFragment.newInstance(
                project.id, project.title, project.description
            )
            val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fl_wrapper, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        return v
    }

    private fun createTask(context: Context, task: Task) {
        ServerEndpoint.addTaskToProject(context, project.id, task, {
            Toast.makeText(activity, "Task was added successfully!", Toast.LENGTH_SHORT).show()
            val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
            fragmentManager.popBackStack()
        }, {
            Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
        })
    }

    companion object {
        @JvmStatic
        fun newInstance(project_id: Int, project_name: String, project_description: String, maintainer_name: String) =
            TasksFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, project_id)
                    putString(ARG_PARAM2, project_name)
                    putString(ARG_PARAM3, project_description)
                    putString(ARG_PARAM4, maintainer_name)
                }
            }
    }
}