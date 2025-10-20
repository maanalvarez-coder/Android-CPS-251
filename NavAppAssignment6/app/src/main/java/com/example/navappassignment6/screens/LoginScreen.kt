package com.example.navappassignment6.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.test.espresso.base.Default
import com.example.navappassignment6.modelViews.LoginView
import com.example.navappassignment6.modelViews.SharedView

@Composable
fun LoginScreen(
    loginView: LoginView = viewModel(),
    sharedView: SharedView = viewModel(),
    onHomeClick: (String) -> Unit,

){
    var text by remember { mutableStateOf("") }
    Column(
        //onHomeClick("")
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(text = "Student Login", fontSize = 30.sp )
        Card(modifier = Modifier
            .size(400.dp)  // Set the size of the box
            .padding(16.dp)  // Add space around the box
            .background(Color.LightGray, shape = RoundedCornerShape(8.dp)),
            elevation = CardDefaults.cardElevation(8.dp),
            shape = RoundedCornerShape(8.dp)
        ){
            Column(modifier = Modifier.padding(10.dp)) {
                OutlinedTextField(
                    value = text,
                    onValueChange = { text = it },
                    label = { Text("Full Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    value = loginView.email,
                    onValueChange = { loginView.changeEmail(it) },
                    label = { Text("Email") },
                    isError = loginView.currentEmail == loginView.email && loginView.loginPressed && !loginView.checkEmail(loginView.email) || loginView.email.isNotEmpty() && loginView.isEmailValid(loginView.email) ,
                    supportingText = {
                        if ( loginView.email.isNotEmpty() && loginView.isEmailValid(loginView.email)){
                            Text("Please enter a valid email")
                        }
                        else if (loginView.currentEmail == loginView.email &&!loginView.checkEmail(loginView.email) && loginView.loginPressed && loginView.email.isNotEmpty()){

                            Text("Wrong email please try again")
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    value = loginView.password,
                    onValueChange = { loginView.changePassword(it) },
                    label = { Text("Password") },
                    visualTransformation = if(loginView.passwordVisable){
                        PasswordVisualTransformation()
                    } else{
                        VisualTransformation.None
                    }, trailingIcon = {
                        val image =
                            if(loginView.passwordVisable)
                                Icons.Default.Visibility
                            else
                                Icons.Default.VisibilityOff
                        IconButton(onClick = {loginView.toggleVis() }) {Icon(imageVector = image, contentDescription = "show?")}
                                      },
                    isError =  loginView.currentPassword == loginView.password &&loginView.loginPressed && loginView.password.isNotEmpty() && !loginView.checkPassword(loginView.password),
                    supportingText ={
                        if( loginView.currentPassword == loginView.password &&loginView.loginPressed && loginView.password.isNotEmpty() && !loginView.checkPassword(loginView.password)){
                        Text("Wrong Password")}
                                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Button(
                onClick = {if (
                    loginView.checkPassword(loginView.password)
                    &&
                    loginView.checkEmail(loginView.email))
                {
                    onHomeClick("")
                    sharedView.updateUser(text)
                }else
                    loginView.pressLogin()
                          }
                , modifier = Modifier.fillMaxWidth().height(50.dp)) {
                Text("Login")
            }
        }
    }
}