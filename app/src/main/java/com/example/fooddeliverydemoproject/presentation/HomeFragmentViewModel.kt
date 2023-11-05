package com.example.fooddeliverydemoproject.presentation

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fooddeliverydemoproject.R
import com.example.fooddeliverydemoproject.data.data_source.database.models.MyCategory
import com.example.fooddeliverydemoproject.data.data_source.database.models.MyMeal
import com.example.fooddeliverydemoproject.domain.CategoryRepository
import com.example.fooddeliverydemoproject.domain.FoodApiRepository
import com.example.fooddeliverydemoproject.domain.MealsRepository
import com.example.fooddeliverydemoproject.data.data_source.retrofit.CategoriesList
import com.example.fooddeliverydemoproject.data.data_source.retrofit.MealsByCategory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragmentViewModel(private val repository: FoodApiRepository, context: Context,
    private val mealsRepository: MealsRepository, private val categoryRepository: CategoryRepository)
    : ViewModel() {

    private val _categoriesResponse = MutableLiveData<CategoriesList?>(null)
    val categoriesResponse
        get() = _categoriesResponse

    private val _mealsByCategoryResponse = MutableLiveData<MealsByCategory?>(null)
    val mealsByCategoryResponse
        get() = _mealsByCategoryResponse

    private val _internetConnectionState = MutableLiveData(false)
    val internetConnectionState
        get() = _internetConnectionState


    init {

        viewModelScope.launch {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkCallback = object : ConnectivityManager.NetworkCallback() {

                override fun onAvailable(network: Network) {
                    _internetConnectionState.postValue(true)
                    viewModelScope.launch {
                        getCategories()
                        getMealsByCategory(context.getString(R.string.beef))
                    }

                }
                override fun onLost(network: Network) {
                    _internetConnectionState.postValue(false)
                }
            }
            connectivityManager.registerDefaultNetworkCallback(networkCallback)
        }

    }

    fun getCategoriesFromDb(): List<MyCategory> {
        return categoryRepository.getAllCategories()
    }

    fun getMealsFromDb(): List<MyMeal> {
        return mealsRepository.getAllMeals()
    }

    fun getCategories() {
        viewModelScope.launch {
            repository.getCategories().enqueue(object : Callback<CategoriesList> {
                override fun onResponse(call: Call<CategoriesList>, response: Response<CategoriesList>) {
                    if (response.body() == null) {
                        _categoriesResponse.postValue(null)
                        return
                    }
                    _categoriesResponse.postValue(response.body())
                    val categoriesList = response.body()?.categories
                    if (categoriesList != null) {
                        viewModelScope.launch {
                            withContext(Dispatchers.IO) {
                                val job = launch {
                                    categoryRepository.deleteAllCategories()
                                }
                                job.join()
                                val myCategoriesList = categoriesList.map {
                                    MyCategory(id = 0, categoryName = it.strCategory)
                                }
                                launch {
                                    for (category in myCategoriesList) {
                                        categoryRepository.insertCategory(category)
                                    }
                                }
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<CategoriesList>, t: Throwable) {
                    _categoriesResponse.postValue(null)
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
                        _mealsByCategoryResponse.postValue(null)
                        return
                    }
                    _mealsByCategoryResponse.postValue(response.body())
                    val mealsList = response.body()?.meals
                    if (mealsList != null) {
                        viewModelScope.launch {
                            withContext(Dispatchers.IO) {
                                val job = launch {
                                    mealsRepository.deleteAllMeals()
                                }
                                job.join()
                                val myMealsList = mealsList.map {
                                    MyMeal(id = 0, mealName = it.strMeal, mealThumb = it.strMealThumb)
                                }
                                launch {
                                    for (meal in myMealsList) {
                                        mealsRepository.insertMeal(meal)
                                    }
                                }
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<MealsByCategory>, t: Throwable) {
                    _mealsByCategoryResponse.postValue(null)
                    t.printStackTrace()
                }
            })
        }
    }

}