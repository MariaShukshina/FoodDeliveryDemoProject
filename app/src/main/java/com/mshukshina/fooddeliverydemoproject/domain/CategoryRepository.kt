package com.mshukshina.fooddeliverydemoproject.domain

import com.mshukshina.fooddeliverydemoproject.data.data_source.database.models.MyCategory

interface CategoryRepository {

    fun getAllCategories(): List<MyCategory>

    suspend fun deleteAllCategories()

    suspend fun insertCategory(category: MyCategory)
}
