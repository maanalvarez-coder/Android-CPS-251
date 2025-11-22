package com.example.budgetapplication.views

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.budgetapplication.temp_objects.Transaction

class LazyCardView: ViewModel() {


    var listItems by mutableStateOf(false)
    var selectedTransaction by mutableStateOf<Transaction?>(null)
    val oneTransaction = Transaction().apply { addTransactions() }

    val twoTransaction = Transaction().apply { addTransactions() }

    var transactionList = listOf(oneTransaction, twoTransaction)

    var expandlist by mutableStateOf(false)

}