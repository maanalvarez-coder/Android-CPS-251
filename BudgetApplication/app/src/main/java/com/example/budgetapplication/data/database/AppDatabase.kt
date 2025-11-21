package com.example.budgetapplication.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.budgetapplication.data.dao.*
import com.example.budgetapplication.data.entities.*

@Database(
    entities = [
        Budget::class,
        Category::class,
        Receipt::class,
        Merchant::class,
        Item::class,
        User::class
    ],
    version = 1,
    exportSchema = false
)

@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun budgetDAO(): BudgetDAO
    abstract fun categoryDAO(): CategoryDAO
    abstract fun receiptDAO(): ReceiptDAO
    abstract fun merchantDAO(): MerchantDAO
    abstract fun itemDAO(): ItemDAO
    abstract fun userDAO(): UserDAO
}
