package com.example.budgetapplication.data.dao

import androidx.room.*
import com.example.budgetapplication.data.entities.Category

@Dao
interface CategoryDAO {

    @Insert
    suspend fun insert(category: Category): Long

    //FOR HOME SCREEN
    @Query("SELECT * FROM CATEGORY")
    suspend fun getAllCategories(): List<Category>
}
