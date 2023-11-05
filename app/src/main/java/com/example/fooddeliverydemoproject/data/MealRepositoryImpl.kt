package com.example.fooddeliverydemoproject.data

import com.example.fooddeliverydemoproject.data.data_source.database.MealDao
import com.example.fooddeliverydemoproject.data.data_source.database.models.MyMeal
import com.example.fooddeliverydemoproject.domain.MealsRepository

class MealRepositoryImpl(private val dao: MealDao): MealsRepository {
    override fun getAllMeals(): List<MyMeal> {
        return dao.getAllMeals()
    }

    override suspend fun insertMeal(meal: MyMeal) {
        dao.insertMeal(meal)
    }

    override suspend fun deleteAllMeals() {
        dao.deleteAllMeals()
    }

}