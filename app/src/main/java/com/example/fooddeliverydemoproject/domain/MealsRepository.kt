package com.example.fooddeliverydemoproject.domain

import com.example.fooddeliverydemoproject.data.data_source.database.models.MyMeal

interface MealsRepository {

    fun getAllMeals(): List<MyMeal>

    suspend fun deleteAllMeals()

    suspend fun insertMeal(meal: MyMeal)
}