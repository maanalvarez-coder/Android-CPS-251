package com.example.budgetapplication.data.dao

import androidx.room.*
import com.example.budgetapplication.data.entities.Budget

@Dao
interface BudgetDAO {

    @Insert
    suspend fun insert(budget: Budget): Long

    //FOR HOME SCREEN
    @Query("SELECT * FROM BUDGET")
    suspend fun getAllBudgets(): List<Budget>
}
