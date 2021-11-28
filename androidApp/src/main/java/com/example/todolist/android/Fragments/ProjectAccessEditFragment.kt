package com.example.todolist.android.Fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.android.Adapter.UsersRecycleViewAdapter
import com.example.todolist.android.R
import com.example.todolist.android.ServerEndpoint


private const val ARG_PARAM1 = "project_id"

class ProjectAccessEditFragment : Fragment() {

    private val usersRecycleViewAdapter by lazy { UsersRecycleViewAdapter() }
    private lateinit var usersRecycleView: RecyclerView
    private var projectId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            projectId = it.getInt(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_project_access_edit, container, false)

        updateUsersList(v.context)

        usersRecycleView = v.findViewById(R.id.users_recycle_view)
        usersRecycleView.adapter = usersRecycleViewAdapter
        usersRecycleView.layoutManager = LinearLayoutManager(activity)
        usersRecycleViewAdapter.setDeleteButtonCallback {
            deleteUser(v.context, it)
        }

        val addBtn : Button = v.findViewById(R.id.add_user_button)
        addBtn.setOnClickListener {
            val emailInput : EditText = v.findViewById(R.id.user_email_input)
            val email: String = emailInput.text.toString()
            if (email == "") {
                Toast.makeText(activity, "Email field can't be empty!", Toast.LENGTH_SHORT).show()
            } else {
                addUser(v.context, email)
            }
        }
        return v
    }

    private fun updateUsersList(context: Context) {
        ServerEndpoint.getProjectUsers(context, projectId!!, {
            usersRecycleViewAdapter.setData(it)
        }, {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        })
    }

    private fun addUser(context: Context, email: String) {
        ServerEndpoint.addUserToProject(context, projectId!!, email, {
            Toast.makeText(activity, "User was added successfully!", Toast.LENGTH_SHORT).show()
            updateUsersList(context)
        }, {
            Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
            updateUsersList(context)
        })
    }

    private fun deleteUser(context: Context, email: String) {
        ServerEndpoint.deleteUserFromProject(context, projectId!!, email, {
            Toast.makeText(activity, "User was deleted successfully!", Toast.LENGTH_SHORT).show()
            updateUsersList(context)
        }, {
            Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
            updateUsersList(context)
        })
    }

    companion object {
        @JvmStatic
        fun newInstance(project_id: Int) =
            ProjectAccessEditFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, project_id)
                }
            }
    }
}