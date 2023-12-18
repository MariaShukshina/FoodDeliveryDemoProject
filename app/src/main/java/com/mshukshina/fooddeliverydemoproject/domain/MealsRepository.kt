package com.mshukshina.fooddeliverydemoproject.domain

import com.mshukshina.fooddeliverydemoproject.data.data_source.database.models.MyMeal

interface MealsRepository {

    fun getAllMeals(): List<MyMeal>

    suspend fun deleteAllMeals()

    suspend fun insertMeal(meal: MyMeal)
}