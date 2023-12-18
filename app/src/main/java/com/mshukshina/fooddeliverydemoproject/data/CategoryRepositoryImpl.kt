package com.mshukshina.fooddeliverydemoproject.data

import com.mshukshina.fooddeliverydemoproject.data.data_source.database.CategoryDao
import com.mshukshina.fooddeliverydemoproject.data.data_source.database.models.MyCategory
import com.mshukshina.fooddeliverydemoproject.domain.CategoryRepository

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