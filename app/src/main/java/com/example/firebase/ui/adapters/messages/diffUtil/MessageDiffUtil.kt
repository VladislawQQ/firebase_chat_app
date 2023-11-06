package com.example.firebase.ui.adapters.messages.diffUtil

import androidx.recyclerview.widget.DiffUtil
import com.example.firebase.model.MessageItem
import com.example.firebase.model.User

class MessageDiffUtil : DiffUtil.ItemCallback<MessageItem>() {

    override fun areItemsTheSame(oldItem: MessageItem, newItem: MessageItem):
            Boolean = oldItem == newItem

    override fun areContentsTheSame(oldItem: MessageItem, newItem: MessageItem):
            Boolean = oldItem.text == newItem.text
}