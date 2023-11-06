package com.example.firebase.ui.adapter.diffUtil

import androidx.recyclerview.widget.DiffUtil
import com.example.firebase.model.MessageItem

class MessageDiffUtil : DiffUtil.ItemCallback<MessageItem>() {

    override fun areItemsTheSame(oldItem: MessageItem, newItem: MessageItem):
            Boolean = oldItem == newItem

    override fun areContentsTheSame(oldItem: MessageItem, newItem: MessageItem):
            Boolean = oldItem.text == newItem.text
}