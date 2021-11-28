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
import com.example.todolist.android.models.RegisterUser

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class RegistrationFragment : Fragment() {

    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_registration, container, false)

        sessionManager = SessionManager(v.context)

        val button: Button = v.findViewById(R.id.register_btn)
        button.setOnClickListener {
            register(v)
        }

        return v
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            RegistrationFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    private fun register(v: View) {
        val button: Button = v.findViewById(R.id.register_btn)
        val nameInput = v.findViewById(R.id.name_input) as EditText
        val emailInput = v.findViewById(R.id.email_input) as EditText
        val passwordInput = v.findViewById(R.id.password_input) as EditText
        val password2Input = v.findViewById(R.id.password2_input) as EditText

        val name = nameInput.text.toString()
        val email = emailInput.text.toString()
        val password = passwordInput.text.toString()
        val password2 = password2Input.text.toString()

        if (email == "" || password == "" || name == "" || password2 == "" ) {
            Toast.makeText(
                activity,
                "Not all required fields are filled",
                Toast.LENGTH_LONG
            ).show()
            return
        }
        button.isClickable = false

        val newUser = RegisterUser(name, email, password, password2)
        ServerEndpoint.register(v.context, newUser, {
            button.isClickable = true
            Toast.makeText(activity, "Registration complete", Toast.LENGTH_SHORT).show()
            sessionManager.saveAuthToken(it.access_token)
            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
        }, {
            button.isClickable = true
            Toast.makeText(activity, it, Toast.LENGTH_LONG).show()
        })
    }
}