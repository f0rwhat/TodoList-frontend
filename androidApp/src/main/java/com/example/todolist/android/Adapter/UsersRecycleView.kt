package com.example.todolist.android.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.android.R
import com.example.todolist.android.models.User

class UsersRecycleViewAdapter: RecyclerView.Adapter<UsersRecycleViewAdapter.UserHolder>() {

    private var usersList = emptyList<User>()
    private lateinit var deleteCallBack: (String) -> Unit

    inner class UserHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    {
        val userNameView: TextView = itemView.findViewById(R.id.user_name)
        val userEmailView: TextView = itemView.findViewById(R.id.user_email)
        val deleteButton: ImageButton = itemView.findViewById(R.id.delete_user_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {
        val v = UserHolder(LayoutInflater.from(parent.context).inflate(R.layout.users_layout, parent, false))

        return v
    }

    fun setDeleteButtonCallback(callBack: (String) -> Unit) {
        deleteCallBack = callBack
    }

    override fun getItemCount(): Int {
        return usersList.size
    }

    override fun onBindViewHolder(holder: UserHolder, position: Int) {
        holder.userNameView.text = usersList[position].name
        holder.userEmailView.text = usersList[position].email
        holder.deleteButton.setOnClickListener{
            deleteCallBack(usersList[position].email)
        }
    }

    fun setData(newUsersList: List<User>) {
        usersList = newUsersList
        notifyDataSetChanged()
    }

}