package com.example.budgetapplication.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.budgetapplication.views.SharedView
/*
* 1) add two buttons OCR and manual
* ?) how are we handling the camera portion of the app
*
*
*
*
*
*
*
*
*
* */
@Composable
fun InputSpending(
    onProfileClick: () -> Unit,
    onBackClick: () -> Unit,
    onHomeClick: (String) -> Unit,
    onInputSpendingClick: () -> Unit,
    onViewTransactionClick: () -> Unit,
    sharedView: SharedView,
){
    Scaffold(
        onProfileClick = {onProfileClick() },
        onHomeClick = {onHomeClick("")},
        onInputSpendingClick = {onInputSpendingClick()},
        onViewTransactionClick = {onViewTransactionClick() },
        sharedView = sharedView
    )
    LazyColumn {
        item { Button(onClick = {}) {Text("OCR") } }
        item { Button(onClick = {}) {Text("Manual input") } }
    }
        Text(
            text = "Input Spending",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )
    }
