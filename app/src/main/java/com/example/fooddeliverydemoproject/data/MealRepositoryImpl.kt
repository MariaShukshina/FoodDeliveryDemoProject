package com.example.fooddeliverydemoproject.data

import androidx.lifecycle.LiveData
import com.example.fooddeliverydemoproject.data_source.database.MealDao
import com.example.fooddeliverydemoproject.data_source.database.MyMeal
import com.example.fooddeliverydemoproject.domain.MealsRepository

class MealRepositoryImpl(private val dao: MealDao): MealsRepository {
    override fun getAllMeals(): LiveData<List<MyMeal>> {
        return dao.getAllMeals()
    }

    override suspend fun insertMeal(meal: MyMeal) {
        dao.insertMeal(meal)
    }

    override fun deleteAllMeals() {
        dao.deleteAllMeals()
    }

}