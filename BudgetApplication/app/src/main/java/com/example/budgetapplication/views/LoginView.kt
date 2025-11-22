package com.example.budgetapplication.views

import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class LoginView: ViewModel() {
    private val correctPassword = "password123"
    private val correctEmail = "student@wccnet.edu"
    private val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")
    var enteredEmail by mutableStateOf("")
    var currentPassword by mutableStateOf("")

    var passwordVisable by mutableStateOf(true)
        private set
    var loginPressed by mutableStateOf(false)
        private set

    var email by mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set

    fun checkPasswordForError(): Boolean{
        if(currentPassword == password && loginPressed && password.isNotEmpty() && !checkPassword(password)){
            return true
        }else{return false}
    }
    fun checkEmailForError(): Boolean{
        //email isError Boolean
        if (enteredEmail == email && loginPressed && checkEmail(email)
            || email.isNotEmpty() && isEmailValid(email)){
            return true
        }
        //password isError Boolean

        else{ return false }
    }
    fun passwordSupportingText(): String {
        if (currentPassword == password && loginPressed && password.isNotEmpty() && !checkPassword(password)){
            return "Wrong Password"
        }
        else{return ""}
    }
    fun emailSupportingText(): String {
        if ( email.isNotEmpty() && isEmailValid(email)){
            return "Please enter a valid email"
        }
        else if (enteredEmail == email &&!checkEmail(email) && loginPressed && email.isNotEmpty()){

            return "Wrong email please try again"
        }
        else{
            return ""
        }
    }
    fun pressLogin(){
        loginPressed = true
        enteredEmail = email
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