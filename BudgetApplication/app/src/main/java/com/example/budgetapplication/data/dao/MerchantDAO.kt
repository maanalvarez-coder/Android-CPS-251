package com.example.budgetapplication.data.dao

import androidx.room.*
import com.example.budgetapplication.data.entities.Merchant

@Dao
interface MerchantDAO {

    @Insert
    suspend fun insert(merchant: Merchant): Long

    //FOR VIEW ALL RECEIPTS SCREEN
    @Query("SELECT * FROM MERCHANT WHERE M_MERCHANT_ID = :id")
    suspend fun getMerchantById(id: Int): Merchant
}
