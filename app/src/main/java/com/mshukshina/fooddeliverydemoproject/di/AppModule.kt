package com.mshukshina.fooddeliverydemoproject.di

import androidx.room.Room
import com.mshukshina.fooddeliverydemoproject.data.CategoryRepositoryImpl
import com.mshukshina.fooddeliverydemoproject.data.FoodApiRepositoryImpl
import com.mshukshina.fooddeliverydemoproject.data.MealRepositoryImpl
import com.mshukshina.fooddeliverydemoproject.data.data_source.database.FoodDatabase
import com.mshukshina.fooddeliverydemoproject.domain.CategoryRepository
import com.mshukshina.fooddeliverydemoproject.domain.FoodApiRepository
import com.mshukshina.fooddeliverydemoproject.domain.MealsRepository
import com.mshukshina.fooddeliverydemoproject.presentation.HomeFragmentViewModel
import com.mshukshina.fooddeliverydemoproject.data.data_source.retrofit.FoodApiService
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://www.themealdb.com/api/json/v1/1/"

val appModule = module {

   viewModel { HomeFragmentViewModel(get(), androidContext(), get(), get()) }

    single {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(FoodApiService::class.java)
    }

    single<FoodApiRepository> {
        FoodApiRepositoryImpl(get())
    }

    single {
        Room.databaseBuilder(
            androidContext(),
            FoodDatabase::class.java,
            FoodDatabase.DATABASE_NAME
        ).build()
    }

    single { get<FoodDatabase>().getCategoryDao() }
    single { get<FoodDatabase>().getMealsDao() }

    single<MealsRepository> {
        MealRepositoryImpl(get())
    }

    single<CategoryRepository> {
        CategoryRepositoryImpl(get())
    }

}