package com.example.firebase.ui.main.chat

import androidx.lifecycle.ViewModel
import com.example.firebase.model.MessageItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ChatViewModel : ViewModel() {

    private val _messagesStateFlow = MutableStateFlow(emptyList<MessageItem?>())
    val messagesStateFlow = _messagesStateFlow.asStateFlow()

//    private val _usersStateFlow = MutableStateFlow(emptyList<User?>())
//    val usersStateFlow = _usersStateFlow.asStateFlow()

    fun addMessage(message: MessageItem?) {
        _messagesStateFlow.value = _messagesStateFlow.value.toMutableList().apply {
            add(message)
        }
    }

//    fun saveUser(user: User?) {
//        _usersStateFlow.value = _usersStateFlow.value.toMutableList().apply {
//            add(user)
//        }
//    }
}