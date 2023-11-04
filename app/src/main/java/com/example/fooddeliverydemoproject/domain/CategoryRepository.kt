package com.example.fooddeliverydemoproject.domain

import androidx.lifecycle.LiveData
import com.example.fooddeliverydemoproject.data_source.database.MyCategory
import com.example.fooddeliverydemoproject.data_source.database.MyMeal

interface CategoryRepository {

    fun getAllCategories(): LiveData<List<MyCategory>>

    fun deleteAllCategories()

    suspend fun insertCategory(category: MyCategory)
}
