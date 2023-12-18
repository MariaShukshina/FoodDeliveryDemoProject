package com.mshukshina.fooddeliverydemoproject.data

import com.mshukshina.fooddeliverydemoproject.data.data_source.database.MealDao
import com.mshukshina.fooddeliverydemoproject.data.data_source.database.models.MyMeal
import com.mshukshina.fooddeliverydemoproject.domain.MealsRepository

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