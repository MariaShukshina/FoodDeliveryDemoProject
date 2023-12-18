package com.mshukshina.fooddeliverydemoproject.data.data_source.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mshukshina.fooddeliverydemoproject.data.data_source.database.models.MyMeal


@Dao
interface MealDao {

    @Query("SELECT * FROM meals_table")
    fun getAllMeals(): List<MyMeal>

    @Query("DELETE FROM meals_table")
    suspend fun deleteAllMeals()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeal(meal: MyMeal)

}
