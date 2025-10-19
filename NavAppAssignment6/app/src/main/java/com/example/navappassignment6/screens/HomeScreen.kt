package com.example.navappassignment6.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.navappassignment6.modelViews.LoginView

@Composable
fun HomeScreen(
    onProfileClick: () -> Unit,
    onLogoutClick: () -> Unit,
){

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(text = "Welcome!", fontSize = 50.sp, fontWeight = Bold)
        Text(text = "Hello, PlaceHolder",fontSize = 30.sp)
        Button(onClick = ({onProfileClick()}), modifier = Modifier.width(300.dp)) {
            Text("Go to Profile")
        }
        Button(onClick = ({onLogoutClick()}),modifier = Modifier.width(300.dp).height(50.dp)) {
            Text("logout")
        }

    }

}