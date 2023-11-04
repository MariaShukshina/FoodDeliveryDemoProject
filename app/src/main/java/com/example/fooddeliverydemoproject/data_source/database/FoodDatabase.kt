package com.example.fooddeliverydemoproject.data_source.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MyCategory::class, MyMeal::class], version = 1)

abstract class FoodDatabase : RoomDatabase() {
    abstract fun getCategoryDao(): CategoryDao
    abstract fun getMealsDao(): MealDao

    companion object {
        const val DATABASE_NAME = "FoodDB.db"
    }
}