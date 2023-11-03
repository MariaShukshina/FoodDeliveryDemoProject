package com.example.fooddeliverydemoproject.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fooddeliverydemoproject.domain.FoodApiRepository
import com.example.fooddeliverydemoproject.retrofit.CategoriesList
import com.example.fooddeliverydemoproject.retrofit.MealsByCategory
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragmentViewModel(private val repository: FoodApiRepository): ViewModel() {

    private val _categoriesResponse = MutableLiveData<CategoriesList?>(null)
    val categoriesResponse
        get() = _categoriesResponse

    private val mealsByCategoryResponse = MutableLiveData<MealsByCategory?>(null)
    val mealsByCategory
        get() = mealsByCategoryResponse

    init {
        getCategories()
    }

    private fun getCategories() {
        viewModelScope.launch {
            repository.getCategories().enqueue(object : Callback<CategoriesList> {
                override fun onResponse(call: Call<CategoriesList>, response: Response<CategoriesList>) {
                    if (response.body() == null) {
                        _categoriesResponse.postValue(null)
                        return
                    }
                    _categoriesResponse.postValue(response.body())
                }

                override fun onFailure(call: Call<CategoriesList>, t: Throwable) {
                    t.printStackTrace()
                }
            })
        }
    }

    fun getMealsByCategory(categoryName: String){
        viewModelScope.launch {
            repository.getMealsByCategory(categoryName).enqueue(object: Callback<MealsByCategory>{
                override fun onResponse(
                    call: Call<MealsByCategory>,
                    response: Response<MealsByCategory>
                ) {
                    if (response.body() == null) {
                        mealsByCategoryResponse.postValue(null)
                        return
                    }
                    mealsByCategoryResponse.postValue(response.body())
                }

                override fun onFailure(call: Call<MealsByCategory>, t: Throwable) {
                    t.printStackTrace()
                }
            })
        }
    }

}