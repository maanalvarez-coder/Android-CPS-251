package com.example.navigation.viewmodels;


class SharedViewModel : ViewModel() {
    var currentUser by mutableStateOf(null)
    private set

    fun updateUser(user: User) {
        currentUser = user
    }

    fun clearUser() {
        currentUser = null
    }
}
