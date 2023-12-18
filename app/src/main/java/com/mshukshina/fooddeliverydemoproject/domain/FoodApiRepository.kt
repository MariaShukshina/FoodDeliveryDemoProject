package com.mshukshina.fooddeliverydemoproject.domain

import com.mshukshina.fooddeliverydemoproject.data.data_source.retrofit.CategoriesList
import com.mshukshina.fooddeliverydemoproject.data.data_source.retrofit.MealsByCategory
import retrofit2.Call

interface FoodApiRepository {

    fun getCategories(): Call<CategoriesList>

    fun getMealsByCategory(categoryName: String): Call<MealsByCategory>
}