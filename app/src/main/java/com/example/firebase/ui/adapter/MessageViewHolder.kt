package com.example.firebase.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.firebase.databinding.RvItemBinding
import com.example.firebase.model.MessageItem

class MessageViewHolder(
    private val binding: RvItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(message : MessageItem) {
        with(binding) {
            name.text = message.name
            msg.text = message.text
        }
    }
}