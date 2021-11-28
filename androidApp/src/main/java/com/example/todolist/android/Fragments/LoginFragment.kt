package com.example.todolist.android.Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.todolist.android.Activities.MainActivity
import com.example.todolist.android.Api.SessionManager
import com.example.todolist.android.R
import com.example.todolist.android.ServerEndpoint

class LoginFragment : Fragment() {

    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v: View = inflater.inflate(R.layout.fragment_login, container, false)
        sessionManager = SessionManager(v.context)
        sessionManager.fetchAuthToken().let {
            ServerEndpoint.getCurrentUser(v.context, {
                val intent = Intent(activity, MainActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }, {})
        }

        val logButton: Button = v.findViewById(R.id.login_btn)
        logButton.setOnClickListener {
            login(v)
        }

        val regButton: Button = v.findViewById(R.id.register_btn)
        regButton.setOnClickListener {
            val regFragment : RegistrationFragment = RegistrationFragment()
            requireActivity().supportFragmentManager.beginTransaction().apply {
                replace(R.id.fl_wrapper, regFragment)
                commit()
            }
        }

        return v
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            LoginFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    private fun login(v: View) {
        val logButton: Button = v.findViewById(R.id.login_btn)
        val regButton: Button = v.findViewById(R.id.register_btn)
        val emailInput = v.findViewById(R.id.email_input) as EditText
        val passwordInput = v.findViewById(R.id.password_input) as EditText
        val email = emailInput.text.toString()
        val password = passwordInput.text.toString()
        if (email == "" || password == "") {
            Toast.makeText(
                activity,
                "Not all required fields are filled",
                Toast.LENGTH_LONG
            ).show()
            return
        }
        logButton.isClickable = false
        regButton.isClickable = false

        ServerEndpoint.login(v.context, email, password, {
            sessionManager.saveAuthToken(it.access_token)
            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
        }, {
            Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
            logButton.isClickable = true
            regButton.isClickable = true
        })
    }
}