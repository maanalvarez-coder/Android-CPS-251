package com.example.budgetapplication.screens.main_screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.budgetapplication.screens.Scaffold
import com.example.budgetapplication.views.SharedView

@Composable
fun ViewTransactions(
    onProfileClick: () -> Unit,
    onBackClick: () -> Unit,
    onHomeClick: (String) -> Unit,
    onInputSpendingClick: () -> Unit,
    onViewTransactionClick: () -> Unit,
    sharedView: SharedView,
) {
    Scaffold(
        onProfileClick = { onProfileClick() },
        onHomeClick = { onHomeClick("") },
        onInputSpendingClick = { onInputSpendingClick() },
        onViewTransactionClick = { onViewTransactionClick() },
        sharedView = sharedView
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "View Transaction",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

