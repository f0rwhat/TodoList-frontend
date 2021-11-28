package com.example.todolist.android.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.todolist.android.Fragments.LoginFragment
import com.example.todolist.android.R

class AuthorisationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authorisation)
        val loginFragment = LoginFragment()
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_wrapper, loginFragment)
            commit()
        }
    }

}