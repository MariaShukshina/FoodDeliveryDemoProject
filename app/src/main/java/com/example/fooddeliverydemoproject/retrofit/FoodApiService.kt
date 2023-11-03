package com.example.fooddeliverydemoproject.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface FoodApiService {
    @GET("categories.php")
    fun getCategories(): Call<CategoriesList>

    @GET("filter.php")
    fun getMealsByCategory(@Query("c") categoryName: String): Call<MealsByCategory>

}