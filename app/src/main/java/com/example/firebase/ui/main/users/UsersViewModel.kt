package com.example.firebase.ui.main.users

import androidx.lifecycle.ViewModel
import com.example.firebase.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class UsersViewModel : ViewModel() {

    private val _userStateFlow = MutableStateFlow(emptyList<User?>())
    val userStateFlow = _userStateFlow.asStateFlow()

    fun addUser (user: User?) {
        _userStateFlow.value = _userStateFlow.value.toMutableList().apply {
            add(user)
        }
    }
}