package com.example.fooddeliverydemoproject.domain

import com.example.fooddeliverydemoproject.data_source.retrofit.CategoriesList
import com.example.fooddeliverydemoproject.data_source.retrofit.MealsByCategory
import retrofit2.Call

interface FoodApiRepository {

    fun getCategories(): Call<CategoriesList>

    fun getMealsByCategory(categoryName: String): Call<MealsByCategory>
}