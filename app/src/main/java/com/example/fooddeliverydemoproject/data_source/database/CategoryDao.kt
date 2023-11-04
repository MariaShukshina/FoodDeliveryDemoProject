package com.example.fooddeliverydemoproject.data_source.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CategoryDao {

    @Query("SELECT * FROM categories_table")
    fun getAllCategories(): LiveData<List<MyCategory>>

    @Query("DELETE FROM categories_table")
    fun deleteAllCategories()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: MyCategory)

}
