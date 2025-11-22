package com.example.budgetapplication.screens.main_screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.budgetapplication.temp_objects.Transaction
import com.example.budgetapplication.views.LazyCardView
import com.example.budgetapplication.views.SharedView

@Composable
fun ViewTransactions(
    onProfileClick: () -> Unit,
    onBackClick: () -> Unit,
    onHomeClick: (String) -> Unit,
    onInputSpendingClick: () -> Unit,
    onViewTransactionClick: () -> Unit,
    sharedView: SharedView,
    lazyCardView: LazyCardView
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 40.dp),
        contentAlignment = Alignment.Center
    ) {
        Column {

            if (!lazyCardView.listItems) {
                Box(modifier = Modifier.wrapContentSize(Alignment.TopStart)) {
                    Button(onClick = {
                        lazyCardView.expandlist = !lazyCardView.expandlist
                    }) {
                        Text("Categories")
                    }
                    DropdownMenu(
                        expanded = lazyCardView.expandlist,
                        onDismissRequest = { lazyCardView.listItems = false },
                    ) {
                        DropdownMenuItem(
                            text = {Text("Category Option A")},
                            onClick = {lazyCardView.expandlist = false},
                        )
                        DropdownMenuItem(
                            text = {Text("Category Option B")},
                            onClick = {lazyCardView.expandlist = false},
                        )
                        DropdownMenuItem(
                            text = {Text("Category Option C")},
                            onClick = {lazyCardView.expandlist = false},
                        )
                    }
                }


//                Button(onClick = {}) { Text("Category")}
//
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(items = lazyCardView.transactionList) { item ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Card(item, {
                                lazyCardView.listItems = true
                                lazyCardView.selectedTransaction = item
                            })
                        }
                    }
                }
            } else {
                Text(
                    text = lazyCardView.selectedTransaction?.merchantName ?: String(),
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text =  lazyCardView.selectedTransaction?.transactionTotal.toString(),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Button(onClick = {lazyCardView.listItems = false}) { Text("back")}
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(items =  lazyCardView.selectedTransaction?.lineItems ?: emptyList()) { item ->

                        Card(item)
                    }

                }

            }
            Scaffold(
                onProfileClick = { onProfileClick() },
                onHomeClick = { onHomeClick("") },
                onInputSpendingClick = { onInputSpendingClick() },
                onViewTransactionClick = { onViewTransactionClick() },
                sharedView = sharedView
            )

        }
    }

}










