package com.example.fooddeliverydemoproject.data

import com.example.fooddeliverydemoproject.data.data_source.database.CategoryDao
import com.example.fooddeliverydemoproject.data.data_source.database.models.MyCategory
import com.example.fooddeliverydemoproject.domain.CategoryRepository

class CategoryRepositoryImpl(private val dao: CategoryDao): CategoryRepository {
    override fun getAllCategories(): List<MyCategory> {
        return dao.getAllCategories()
    }

    override suspend fun deleteAllCategories() {
        dao.deleteAllCategories()
    }

    override suspend fun insertCategory(category: MyCategory) {
        dao.insertCategory(category)
    }

}