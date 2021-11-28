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
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.todolist.android.R
import com.example.todolist.android.ServerEndpoint
import com.example.todolist.android.models.Project

private const val ARG_PARAM1 = "project_id"
private const val ARG_PARAM2 = "project_title"
private const val ARG_PARAM3 = "project_description"

class ProjectEditFragment : Fragment() {
    private var projectId: Int? = null
    private var projectTitle: String? = null
    private var projectDescription: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            projectId = it.getInt(ARG_PARAM1)
            projectTitle = it.getString(ARG_PARAM2)
            projectDescription = it.getString(ARG_PARAM3)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_project_edit, container, false)

        val titleInput : EditText = v.findViewById(R.id.project_title_input)
        val descriptionInput : EditText = v.findViewById(R.id.project_description_input)
        titleInput.setText(projectTitle)
        descriptionInput.setText(projectDescription)

        val confirmBtn : Button = v.findViewById(R.id.confirm_button)
        confirmBtn.setOnClickListener {
            if (projectTitle != titleInput.text.toString()
                || projectDescription != descriptionInput.text.toString()) {
                    if (titleInput.text.toString().isEmpty()) {
                        Toast.makeText(activity, "Project title can't be empty!", Toast.LENGTH_SHORT).show()
                    } else {
                        updateProject(v.context, titleInput.text.toString(), descriptionInput.text.toString())
                    }
            }
        }

        val closeProjectBtn : Button = v.findViewById(R.id.close_project_button)
        closeProjectBtn.setOnClickListener {
            lateinit var dialog: AlertDialog
            val builder = AlertDialog.Builder(it.context)
            builder.setTitle("Do really want to close this project?")
            val dialogClickListener = DialogInterface.OnClickListener{ _, which ->
                when(which){
                    DialogInterface.BUTTON_POSITIVE -> {
                        deleteProject(v.context)
                    }
                    DialogInterface.BUTTON_NEUTRAL -> {}
                }
            }
            builder.setPositiveButton("YES",dialogClickListener)
            builder.setNeutralButton("CANCEL",dialogClickListener)
            dialog = builder.create()
            dialog.show()
        }

        val editAccessBtn : Button = v.findViewById(R.id.access_edit_button)
        editAccessBtn.setOnClickListener {
            val fragment: ProjectAccessEditFragment = ProjectAccessEditFragment.newInstance(projectId!!)
            val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fl_wrapper, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
        return v
    }

    private fun deleteProject(context: Context) {
        ServerEndpoint.deleteProject(context, projectId!!, {
            Toast.makeText(activity, "Project was deleted successfully!", Toast.LENGTH_SHORT).show()
            val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
            fragmentManager.popBackStack()
            fragmentManager.popBackStack()
        }, {
            Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
        })
    }

    private fun updateProject(context: Context, title: String, description: String) {
        ServerEndpoint.updateProject(context, projectId!!, Project(title, description), {
            Toast.makeText(activity, "Project was updated successfully!", Toast.LENGTH_SHORT).show()
            projectTitle = title
            projectDescription = description
        }, {
            Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
        })
    }

    companion object {
        @JvmStatic
        fun newInstance(project_id: Int, project_title: String, project_description: String) =
            ProjectEditFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, project_id)
                    putString(ARG_PARAM2, project_title)
                    putString(ARG_PARAM3, project_description)
                }
            }
    }
}