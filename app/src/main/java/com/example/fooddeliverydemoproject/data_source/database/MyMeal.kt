package com.example.fooddeliverydemoproject.data_source.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "meals_table")
data class MyMeal(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val mealName: String,
    val mealThumb: String
)
