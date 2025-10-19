package com.example.navappassignment6.modelViews

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel


class LoginView: ViewModel() {
    private val correctPassword = "password123"
    private val correctEmail = "student@wccnet.edu"
    private val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")
    var currentEmail by mutableStateOf("")
    var currentPassword by mutableStateOf("")

    var passwordVisable by mutableStateOf(false)
        private set
    var loginPressed by mutableStateOf(false)
    private set

    var email by mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set

    fun pressLogin(){
        loginPressed = true
        currentEmail = email
        currentPassword = password

    }
    fun toggleVis(){
        if (passwordVisable)
            passwordVisable = false
        else
            passwordVisable =true
    }
    fun isEmailValid(email: String): Boolean{
        if(email.matches(emailRegex))
            return false
        else
            return true
    }
    fun changeEmail(newEmail : String) {
        email = newEmail
    }
    fun changePassword(newPassword : String) {
        password = newPassword
    }
    fun checkPassword(password : String): Boolean {
        if (correctPassword == password)
            return true
        else
            return false
    }

    fun checkEmail(email : String): Boolean {
        if (correctEmail == email)
            return true
        else
            return false
    }

}