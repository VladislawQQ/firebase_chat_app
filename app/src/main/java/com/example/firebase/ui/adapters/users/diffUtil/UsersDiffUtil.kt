package com.example.firebase.ui.adapters.users.diffUtil

import androidx.recyclerview.widget.DiffUtil
import com.example.firebase.model.User

class UsersDiffUtil : DiffUtil.ItemCallback<User>() {

    override fun areItemsTheSame(oldItem: User, newItem: User):
            Boolean = oldItem == newItem

    override fun areContentsTheSame(oldItem: User, newItem: User):
            Boolean = oldItem.id == newItem.id
}