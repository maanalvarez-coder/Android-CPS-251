package com.example.budgetapplication.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
import androidx.room.Index
import kotlinx.datetime. *

@Entity(
    tableName = "CATEGORY",
    foreignKeys = [
        ForeignKey(
            entity = Budget::class,
            parentColumns = ["B_BUDGET_ID"],
            childColumns = ["C_BUDGET_ID"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["C_BUDGET_ID"]),
    ]
)
data class Category(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "C_CATEGORY_ID")
    val categoryId_i: Int = 0,

    @ColumnInfo(name = "C_BUDGET_ID")
    val budgetId_i: Int?,

    @ColumnInfo(name = "C_CATEGORY_NAME")
    val categoryName_s: String,

    @ColumnInfo(name = "C_CATEGORY_LIMIT")
    val categoryLimit_s: String,

    @ColumnInfo(name = "C_OUT")
    val categoryOut_b: Boolean

)
