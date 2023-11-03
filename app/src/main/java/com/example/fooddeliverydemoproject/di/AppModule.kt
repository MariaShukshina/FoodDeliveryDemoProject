package com.example.fooddeliverydemoproject.di

import com.example.fooddeliverydemoproject.data.FoodApiRepositoryImpl
import com.example.fooddeliverydemoproject.domain.FoodApiRepository
import com.example.fooddeliverydemoproject.presentation.HomeFragmentViewModel
import com.example.fooddeliverydemoproject.retrofit.FoodApiService
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://www.themealdb.com/api/json/v1/1/"

val appModule = module {

   viewModel { HomeFragmentViewModel(get(), androidContext()) }

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

}