package com.example.firebase.ui.adapters.messages

import android.view.View.GONE
import androidx.recyclerview.widget.RecyclerView
import com.example.firebase.databinding.ItemMessageBinding
import com.example.firebase.model.MessageItem
import com.example.firebase.ui.utils.ext.setImage

class MessageViewHolder(
    private val binding: ItemMessageBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(message : MessageItem) {
        with(binding) {
            name.text = message.name

            msg.text = message.text.ifBlank {
                msg.visibility = GONE
            }.toString()

            imageViewImage.setImage(message.imgUrl)
        }
    }
}