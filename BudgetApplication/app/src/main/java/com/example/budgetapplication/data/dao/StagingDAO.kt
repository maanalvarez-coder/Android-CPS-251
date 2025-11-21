package com.example.budgetapplication.data.dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.budgetapplication.data.entities.Staging

@Dao
interface StagingDAO {

    @Insert
    suspend fun insertOCRStaging(staging: Staging)

}
