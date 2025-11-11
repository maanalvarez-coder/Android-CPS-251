package com.example.budgetapplication.screens.main_screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.budgetapplication.views.SharedView


@Composable
fun ProfileScreen(
    onProfileClick: () -> Unit,
    onBackClick: () -> Unit,
    onHomeClick: (String) -> Unit,
    onInputSpendingClick: () -> Unit,
    onViewTransactionClick: () -> Unit,
    sharedView: SharedView,
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
        Text(text = "User Profile", fontSize = 50.sp )
        Card(modifier = Modifier
            .size(400.dp)  // Set the size of the box
            .padding(16.dp)  // Add space around the box
            .background(Color.LightGray, shape = RoundedCornerShape(8.dp)),
            elevation = CardDefaults.cardElevation(8.dp),
            shape = RoundedCornerShape(8.dp)



        ){


            Row (modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.spacedBy(20
                    .dp, Alignment.CenterHorizontally)){
                FlowColumn (
                    modifier =  Modifier.weight(1f).padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)

                ) {
                    Text("Name:")

                }
                FlowColumn (
                    modifier =  Modifier.weight(1f).padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)

                ) {
                    Text(sharedView.currentUser)
                }
            }



        }

          }
}