package com.example.budgetapplication.screens.main_screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.budgetapplication.views.SharedView

@Composable
fun HomeScreen(
    onProfileClick: () -> Unit,
    onLogoutClick: () -> Unit,
    onHomeClick: (String) -> Unit,
    onInputSpendingClick: () -> Unit,
    onViewTransactionClick: () -> Unit,
    sharedView: SharedView,
    onCreateBudgetClick: () -> Unit,
){
    Scaffold(
        onProfileClick = { onProfileClick() },
        onHomeClick = { onHomeClick("") },
        onInputSpendingClick = { onInputSpendingClick() },
        onViewTransactionClick = { onViewTransactionClick() },
        sharedView = sharedView
    )
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(text = "Welcome!", fontSize = 50.sp, fontWeight = Bold)
        LazyRow {
            item{Card()}
            item{Card()}
            item{Card()}
        }
        Button(onClick = ({onCreateBudgetClick()}), modifier = Modifier.width(300.dp)) {
            Text("Create Budget")
        }
        Button(onClick = ({onLogoutClick()}),modifier = Modifier.width(300.dp).height(50.dp)) {
            Text("logout")
        }

    }

}
