package com.example.firebase.ui.adapters.users

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.firebase.databinding.ItemUserBinding
import com.example.firebase.model.User
import com.example.firebase.ui.adapters.users.diffUtil.UsersDiffUtil
import com.example.firebase.ui.main.users.UserActionListener

class UsersAdapter(
    private val userActionListener: UserActionListener
) : ListAdapter<User, UsersViewHolder>(UsersDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemUserBinding.inflate(inflater, parent, false)
        return UsersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

}