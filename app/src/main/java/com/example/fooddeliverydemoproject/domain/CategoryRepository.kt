package com.example.fooddeliverydemoproject.domain

import com.example.fooddeliverydemoproject.data.data_source.database.models.MyCategory

interface CategoryRepository {

    fun getAllCategories(): List<MyCategory>

    suspend fun deleteAllCategories()

    suspend fun insertCategory(category: MyCategory)
}
