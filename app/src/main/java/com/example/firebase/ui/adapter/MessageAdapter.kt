package com.example.firebase.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.firebase.ui.adapter.diffUtil.MessageDiffUtil
import com.example.firebase.databinding.RvItemBinding
import com.example.firebase.model.MessageItem

class MessageAdapter : ListAdapter<MessageItem, MessageViewHolder>(MessageDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding = RvItemBinding.inflate(inflater, parent, false)
        return MessageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

}