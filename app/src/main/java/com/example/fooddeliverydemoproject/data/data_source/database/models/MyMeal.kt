package com.example.fooddeliverydemoproject.data.data_source.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "meals_table")
data class MyMeal(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val mealName: String,
    val mealThumb: String
)
