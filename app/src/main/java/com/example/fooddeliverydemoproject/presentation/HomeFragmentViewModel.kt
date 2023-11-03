package com.example.fooddeliverydemoproject.presentation

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
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

class HomeFragmentViewModel(private val repository: FoodApiRepository, context: Context)
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
                    getCategories()
                    getMealsByCategory("Beef")
                }

                override fun onLost(network: Network) {
                    _internetConnectionState.postValue(false)
                }
            }
            connectivityManager.registerDefaultNetworkCallback(networkCallback)
        }

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
                        _mealsByCategoryResponse.postValue(null)
                        return
                    }
                    _mealsByCategoryResponse.postValue(response.body())
                }

                override fun onFailure(call: Call<MealsByCategory>, t: Throwable) {
                    _mealsByCategoryResponse.postValue(null)
                    t.printStackTrace()
                }
            })
        }
    }

}