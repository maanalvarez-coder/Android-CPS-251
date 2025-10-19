package com.example.navappassignment6.modelViews

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class SharedView: ViewModel() {
    var currentUser: String by mutableStateOf("Scott")
        private set
    fun updateUser(user: String) {
        currentUser = user
    }

    fun clearUser() {
        currentUser = ""
    }
}