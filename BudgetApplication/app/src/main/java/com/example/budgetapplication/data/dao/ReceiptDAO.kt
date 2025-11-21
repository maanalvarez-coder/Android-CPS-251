package com.example.budgetapplication.data.dao

import androidx.room.*
import com.example.budgetapplication.data.entities.Receipt

@Dao
interface ReceiptDAO {

    @Insert
    suspend fun insert(receipt: Receipt): Long

    //FOR VIEW ALL RECEIPTS SCREEN
    @Query("SELECT * FROM RECEIPT ORDER BY R_RECEIPT_DATE DESC, R_RECEIPT_TIME DESC")
    suspend fun getAllReceipts(): List<Receipt>
}
