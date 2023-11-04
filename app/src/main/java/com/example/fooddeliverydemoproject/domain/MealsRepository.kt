package com.example.fooddeliverydemoproject.domain

import androidx.lifecycle.LiveData
import com.example.fooddeliverydemoproject.data_source.database.MyMeal

interface MealsRepository {

    fun getAllMeals(): LiveData<List<MyMeal>>

    fun deleteAllMeals()

    suspend fun insertMeal(meal: MyMeal)
}