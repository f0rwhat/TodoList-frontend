package com.example.todolist.android.Fragments

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.todolist.android.R
import com.example.todolist.android.ServerEndpoint
import com.example.todolist.android.models.Task

private const val ARG_PARAM1 = "project_id"
private const val ARG_PARAM2 = "project_title"
private const val ARG_PARAM3 = "task_id"
private const val ARG_PARAM4 = "task_title"
private const val ARG_PARAM5 = "task_description"
private const val ARG_PARAM6 = "task_priority"

class TaskInfoFragment : Fragment() {
    private var projectId: Int? = null
    private var projectTitle: String? = null
    private var taskId: Int? = null
    private var taskTitle: String? = null
    private var taskDescription: String? = null
    private var taskPriority: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            projectId = it.getInt(ARG_PARAM1)
            projectTitle = it.getString(ARG_PARAM2)
            taskId = it.getInt(ARG_PARAM3)
            taskTitle = it.getString(ARG_PARAM4)
            taskDescription = it.getString(ARG_PARAM5)
            taskPriority = it.getInt(ARG_PARAM6)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v: View = inflater.inflate(R.layout.fragment_task_info, container, false)

        val projectTitleView: TextView = v.findViewById(R.id.project_title)
        val taskTitleView: TextView = v.findViewById(R.id.task_title)
        val taskDescriptionView: TextView = v.findViewById(R.id.task_description)

        projectTitleView.setText(projectTitle!!)
        taskTitleView.setText(taskTitle!!)
        taskDescriptionView.setText(taskDescription!!)

        val closeTaskBtn : Button = v.findViewById(R.id.close_task_button)
        closeTaskBtn.setOnClickListener {
            lateinit var dialog:AlertDialog
            val builder = AlertDialog.Builder(it.context)
            builder.setTitle("Do really want to close this task?")
            val dialogClickListener = DialogInterface.OnClickListener{ _, which ->
                when(which){
                    DialogInterface.BUTTON_POSITIVE -> {
                        deleteTask(v.context)
                    }
                    DialogInterface.BUTTON_NEUTRAL -> {}
                }
            }
            builder.setPositiveButton("YES",dialogClickListener)
            builder.setNeutralButton("CANCEL",dialogClickListener)
            dialog = builder.create()
            dialog.show()
        }

        val editBtn : Button = v.findViewById(R.id.edit_task_button)
        editBtn.setOnClickListener {
            val fragment: TaskCreationFragment = TaskCreationFragment.newInstance(
                projectTitle!!, taskTitle!!, taskDescription!!, taskPriority!!
            )
            fragment.setConfirmCallback { task ->  updateTask(v.context, task) }
            val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fl_wrapper, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
        return v
    }

    private fun updateTask(context: Context, task: Task) {
        ServerEndpoint.updateTask(context, projectId!!, taskId!!, task, {
            Toast.makeText(activity, "Task was updated successfully!", Toast.LENGTH_SHORT).show()
            val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
            fragmentManager.popBackStack()
            fragmentManager.popBackStack()
        }, {
            Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
        })
    }

    private fun deleteTask(context: Context) {
        ServerEndpoint.deleteTaskFromProject(context, projectId!!, taskId!!, {
            Toast.makeText(activity, "Task was closed successfully!", Toast.LENGTH_SHORT).show()
            val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
            fragmentManager.popBackStack()
        }, {
            Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
        })
    }

    companion object {
        @JvmStatic
        fun newInstance(project_id: Int,
                        project_name: String,
                        task_id: Int,
                        task_title: String,
                        task_description: String,
                        task_priority: Int,) =
            TaskInfoFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, project_id)
                    putString(ARG_PARAM2, project_name)
                    putInt(ARG_PARAM3, task_id)
                    putString(ARG_PARAM4, task_title)
                    putString(ARG_PARAM5, task_description)
                    putInt(ARG_PARAM6, task_priority)
                }
            }
    }
}