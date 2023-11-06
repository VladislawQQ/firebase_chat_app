package com.example.firebase.ui.adapters.users

import androidx.recyclerview.widget.RecyclerView
import com.example.firebase.databinding.ItemUserBinding
import com.example.firebase.model.User

class UsersViewHolder(
    private val binding: ItemUserBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(user : User) {
        with(binding) {
//            imageViewProfile.setProfileImage(user.)
            textViewUserName.text = user.name

//            setListeners(user)
        }
    }
}