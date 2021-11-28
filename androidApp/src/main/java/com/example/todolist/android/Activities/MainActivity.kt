package com.example.todolist.android.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.todolist.android.Fragments.ProfileFragment
import com.example.todolist.android.Fragments.ProjectsFragment
import com.example.todolist.android.Fragments.TasksFragment
import com.example.todolist.android.R
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val projectsFragment = ProjectsFragment()
        val profileFragment = ProfileFragment()

        makeCurrentFragment(projectsFragment)
        val bottom_navigation : BottomNavigationView  = findViewById(R.id.bottom_navigation)
        bottom_navigation.setOnNavigationItemReselectedListener {
            when (it.itemId) {
                R.id.ic_projects -> makeCurrentFragment(projectsFragment)
                R.id.ic_profile-> makeCurrentFragment(profileFragment)
            }
        }
    }

    private fun makeCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_wrapper, fragment)
            commit()
        }


}
