package com.example.fooddeliverydemoproject.data_source.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories_table")
data class MyCategory(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val categoryName: String
)
