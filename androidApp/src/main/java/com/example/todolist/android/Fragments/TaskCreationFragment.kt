package com.example.todolist.android.Fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.FragmentManager
import com.example.todolist.android.R
import com.example.todolist.android.models.Task
import android.widget.AdapterView

import android.widget.TextView
import com.example.todolist.android.ServerEndpoint


private const val ARG_PARAM1 = "project_name"
private const val ARG_PARAM2 = "task_title"
private const val ARG_PARAM3 = "task_description"
private const val ARG_PARAM4 = "selected_priority"

class TaskCreationFragment : Fragment() {
    private var projectName: String? = null
    private var taskTitle: String? = ""
    private var taskDescription: String? = ""
    private var selectedPriority: Int? = 1;

    private lateinit var confirmCallback : (Task) -> Unit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            projectName = it.getString(ARG_PARAM1)
            taskTitle = it.getString(ARG_PARAM2)
            taskDescription = it.getString(ARG_PARAM3)
            selectedPriority = it.getInt(ARG_PARAM4)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v: View = inflater.inflate(R.layout.fragment_task_creation, container, false)

        val projectTitleView: TextView = v.findViewById(R.id.project_title)
        val taskTitleInput: EditText = v.findViewById(R.id.task_title_input)
        val taskDescriptionInput: EditText = v.findViewById(R.id.task_description_input)
        val taskPrioritySpinner : Spinner = v.findViewById(R.id.priority_spinner)

        projectTitleView.text = projectName
        taskTitleInput.setText(taskTitle!!)
        taskDescriptionInput.setText(taskDescription!!)
        syncPrioritiesSpinner(v.context, taskPrioritySpinner)

        val confirmBtn : Button = v.findViewById(R.id.confirm_button)
        confirmBtn.setOnClickListener {
            val title = taskTitleInput.text.toString()
            if (title.isEmpty()) {
                Toast.makeText(activity, "Task title cant be empty!", Toast.LENGTH_SHORT).show()
            } else {
                val description = taskDescriptionInput.text.toString()
                val task by lazy{ Task(title, description, selectedPriority!!) }
                confirmCallback(task)
            }
        }

        return v
    }

    fun setConfirmCallback(callBack: (Task) -> Unit) {
        confirmCallback = callBack
    }

    private fun syncPrioritiesSpinner(context: Context, taskPrioritySpinner : Spinner) {
        ServerEndpoint.getPriorities(context, {
            val priorityNames: MutableList<String> = mutableListOf()
            for (priority in it) {
                priorityNames += priority.name
            }
            val spinnerAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, priorityNames)
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            with(taskPrioritySpinner)
            {
                adapter = spinnerAdapter
                setSelection(selectedPriority!! - 1, false)
                onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    // Callback method to invoke when a state has been selected
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View,
                        position: Int,
                        id: Long
                    ) {
                        selectedPriority = (id + 1).toInt()
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        return
                    }
                }
                prompt = "Select priority"
                gravity = android.view.Gravity.CENTER
            }
        }, {
            Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
            val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
            fragmentManager.popBackStack()
        })
    }

    companion object {
        @JvmStatic
        fun newInstance(
                        project_name: String,
                        task_title: String,
                        task_description: String,
                        priority: Int) =
            TaskCreationFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, project_name)
                    putString(ARG_PARAM2, task_title)
                    putString(ARG_PARAM3, task_description)
                    putInt(ARG_PARAM4, priority)
                }
            }
    }
}