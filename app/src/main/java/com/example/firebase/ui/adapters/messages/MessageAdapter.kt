package com.example.firebase.ui.adapters.messages

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.firebase.databinding.ItemMessageBinding
import com.example.firebase.model.MessageItem
import com.example.firebase.ui.adapters.messages.diffUtil.MessageDiffUtil

class MessageAdapter : ListAdapter<MessageItem, MessageViewHolder>(MessageDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemMessageBinding.inflate(inflater, parent, false)
        return MessageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

}