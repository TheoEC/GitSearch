package com.example.githubsearch.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.githubsearch.R
import com.example.githubsearch.domain.User

class UserAdapter(private val lstUsers: List<User>): RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    var userItemListener : (User) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_user, parent, false)
        return UserViewHolder(view)
    }

    override fun getItemCount(): Int = lstUsers.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = lstUsers[position]

        holder.userName.text = user.name
        holder.background.setOnClickListener{
            userItemListener(user)
        }
    }

    class UserViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val userName : TextView
        val background: CardView

        init {
            userName = view.findViewById(R.id.tv_user_name)
            background = view.findViewById(R.id.cb_card)
        }
    }
}