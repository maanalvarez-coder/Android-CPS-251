package com.example.budgetapplication.screens

import androidx.compose.foundation.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.budgetapplication.navigation.NavRoutes
import com.example.budgetapplication.views.LoginView
import com.example.budgetapplication.views.SharedView
//@Composable
//fun Scaffold(){
//    Scaffold(
//        bottomBar = {
//            NavBar(
//                onProfileClick = {onProfileClick() },
//                onHomeClick = {onHomeClick("")},
//                onInputSpendingClick = {onInputSpendingClick()},
//                onViewTransactionClick = {onViewTransactionClick() },
//                sharedView = sharedView)
//        }
//    ){}
//}

@Composable
fun NavBar(
    onProfileClick: () -> Unit,
    onHomeClick: (String) -> Unit,
    onInputSpendingClick: () -> Unit,
    onViewTransactionClick: () -> Unit,
    sharedView: SharedView,
){

    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
            label = { Text("Home") },
            selected = false,
            onClick = {onHomeClick("")}
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.AccountBox, contentDescription = "Profile") },
            label = { Text("Profile") },
            selected = false,
            onClick = {onProfileClick()}
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Add, contentDescription = "Input Spending") },
            label = { Text("Input Spending") },
            selected = false,
            onClick = {onInputSpendingClick()}
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.AccountBalance, contentDescription = "View Transaction") },
            label = { Text("View Transaction") },
            selected = false,
            onClick = {onViewTransactionClick()}
        )

    }
}
@Composable
fun Scaffold(onProfileClick: () -> Unit,
             onHomeClick: (String) -> Unit,
             onInputSpendingClick: () -> Unit,
             onViewTransactionClick: () -> Unit,
             sharedView: SharedView){
    Scaffold(
        bottomBar = {
            NavBar(
                onProfileClick = {onProfileClick() },
                onHomeClick = {onHomeClick("")},
                onInputSpendingClick = {onInputSpendingClick()},
                onViewTransactionClick = {onViewTransactionClick() },
                sharedView = sharedView)
        }
    ){}
}