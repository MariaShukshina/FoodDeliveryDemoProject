package com.example.fooddeliverydemoproject.data

import androidx.lifecycle.LiveData
import com.example.fooddeliverydemoproject.data_source.database.CategoryDao
import com.example.fooddeliverydemoproject.data_source.database.MyCategory
import com.example.fooddeliverydemoproject.domain.CategoryRepository

class CategoryRepositoryImpl(private val dao: CategoryDao): CategoryRepository {
    override fun getAllCategories(): LiveData<List<MyCategory>> {
        return dao.getAllCategories()
    }

    override fun deleteAllCategories() {
        dao.deleteAllCategories()
    }

    override suspend fun insertCategory(category: MyCategory) {
        dao.insertCategory(category)
    }

}