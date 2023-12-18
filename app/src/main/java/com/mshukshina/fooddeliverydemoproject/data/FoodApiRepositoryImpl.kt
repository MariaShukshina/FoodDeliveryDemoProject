package com.mshukshina.fooddeliverydemoproject.data

import com.mshukshina.fooddeliverydemoproject.domain.FoodApiRepository
import com.mshukshina.fooddeliverydemoproject.data.data_source.retrofit.FoodApiService

class FoodApiRepositoryImpl(private val api: FoodApiService): FoodApiRepository {
    override fun getCategories() = api.getCategories()

    override fun getMealsByCategory(categoryName: String) = api.getMealsByCategory(categoryName)
}