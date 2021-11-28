package com.example.todolist.android.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.android.Adapter.ProjectsRecycleViewAdapter
import com.example.todolist.android.R
import com.example.todolist.android.ServerEndpoint


class ProjectsFragment : Fragment() {

    private val projectsRecycleViewAdapter by lazy { ProjectsRecycleViewAdapter() }
    private lateinit var projectsRecycleView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_projects, container, false)

        projectsRecycleView = v.findViewById(R.id.projects_recycle_view)
        projectsRecycleView.adapter = projectsRecycleViewAdapter
        projectsRecycleView.layoutManager = LinearLayoutManager(activity)

        ServerEndpoint.getProjects(v.context, {
            projectsRecycleViewAdapter.setData(it)
        }, {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        })

        val addProjectBtn : Button = v.findViewById(R.id.add_project_button)
        addProjectBtn.setOnClickListener {
            val fragment: Fragment = ProjectCreationFragment()
            val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fl_wrapper, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        return v
    }
}