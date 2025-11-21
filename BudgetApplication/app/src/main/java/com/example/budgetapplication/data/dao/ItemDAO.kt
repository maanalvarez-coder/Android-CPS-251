package com.example.budgetapplication.data.dao

import androidx.room.*
import com.example.budgetapplication.data.entities.Item

@Dao
interface ItemDAO {

    @Insert
    suspend fun insert(item: Item): Long

    //FOR VIEW SCREEN (if there will be a view ALL items filter)
    @Query("SELECT * FROM ITEM ORDER BY I_RECEIPT_DATE DESC, I_RECEIPT_TIME DESC")
    suspend fun getAllItems(): List<Item>

    //FOR INDIVIDUAL RECEIPT SCREEN
    @Query("SELECT * FROM ITEM WHERE I_RECEIPT_ID = :receiptId")
    suspend fun getItemsForReceipt(receiptId: Int): List<Item>

    //FOR INDIVIDUAL CATEGORY SCREEN
    @Query("SELECT * FROM ITEM WHERE I_CATEGORY_ID = :categoryId")
    suspend fun getItemsByCategory(categoryId: Int): List<Item>


}
