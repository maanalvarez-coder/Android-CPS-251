package com.example.budgetapplication.screens.main_screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.budgetapplication.temp_objects.Line_Item
import com.example.budgetapplication.temp_objects.Transaction
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

    NavigationBar() {
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
            icon = { Icon(Icons.Filled.Add, contentDescription = "Input") },
            label = { Text("Input") },
            selected = false,
            onClick = {onInputSpendingClick()}
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.AccountBalance, contentDescription = "View") },
            label = { Text("View") },
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

@Composable
fun Card(lineItem: Line_Item) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = lineItem.name,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = lineItem.price.toString(),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun Card(transaction: Transaction, onItemClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable(onClick = {onItemClick()}),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = transaction.merchantName,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = transaction.transactionTotal.toString(),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
@Composable
fun Card() {
    Card(
        modifier = Modifier.width(400.dp).height(300.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ){
        Text(text = "GRAPH")
    }

}


