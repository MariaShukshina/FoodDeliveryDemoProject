package com.example.fooddeliverydemoproject.data_source.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface MealDao {

    @Query("SELECT * FROM meals_table")
    fun getAllMeals(): LiveData<List<MyMeal>>

    @Query("DELETE FROM meals_table")
    fun deleteAllMeals()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeal(meal: MyMeal)

}
