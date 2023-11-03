package com.example.fooddeliverydemoproject.data

import com.example.fooddeliverydemoproject.domain.FoodApiRepository
import com.example.fooddeliverydemoproject.retrofit.FoodApiService

class FoodApiRepositoryImpl(private val api: FoodApiService): FoodApiRepository {
    override fun getCategories() = api.getCategories()

    override fun getMealsByCategory(categoryName: String) = api.getMealsByCategory(categoryName)
}