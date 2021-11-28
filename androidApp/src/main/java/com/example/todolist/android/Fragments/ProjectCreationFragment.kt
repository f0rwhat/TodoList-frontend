package com.example.todolist.android.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import com.example.todolist.android.R
import com.example.todolist.android.ServerEndpoint
import com.example.todolist.android.models.Project


class ProjectCreationFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_project_creation, container, false)

        val confirmBtn : Button = v.findViewById(R.id.confirm_button)
        confirmBtn.setOnClickListener {
            val projectTitleInput = v.findViewById(R.id.project_title_input) as EditText
            val title = projectTitleInput.text.toString()
            if (title.isEmpty()) {
                Toast.makeText(activity, "Project title cant be empty!", Toast.LENGTH_SHORT).show()
            } else {
                val projectDescriptionInput = v.findViewById(R.id.project_description_input) as EditText
                val description = projectDescriptionInput.text.toString()
                val project by lazy{ Project(title, description) }

                ServerEndpoint.createProject(v.context, project, {
                    Toast.makeText(activity, "Project was created successfully!", Toast.LENGTH_SHORT).show()
                    val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
                    fragmentManager.popBackStack()
                }, {
                    Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
                })
            }
        }

        return v
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProjectCreationFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}