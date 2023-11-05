package com.example.fooddeliverydemoproject.data.data_source.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.fooddeliverydemoproject.data.data_source.database.models.MyCategory

@Dao
interface CategoryDao {

    @Query("SELECT * FROM categories_table")
    fun getAllCategories(): List<MyCategory>

    @Query("DELETE FROM categories_table")
    suspend fun deleteAllCategories()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: MyCategory)

}
