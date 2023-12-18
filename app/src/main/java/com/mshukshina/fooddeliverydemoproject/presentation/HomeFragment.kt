package com.mshukshina.fooddeliverydemoproject.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.mshukshina.fooddeliverydemoproject.R
import com.mshukshina.fooddeliverydemoproject.databinding.FragmentHomeBinding
import com.mshukshina.fooddeliverydemoproject.presentation.utils.Constants
import com.mshukshina.fooddeliverydemoproject.data.data_source.retrofit.Category
import com.mshukshina.fooddeliverydemoproject.data.data_source.retrofit.Meal
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private val viewModel by viewModel<HomeFragmentViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val context = requireContext()

        val bannersAdapter = BannersRvItemsAdapter()
        binding.bannersRv.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL, false
        )
        binding.bannersRv.adapter = bannersAdapter
        bannersAdapter.setImageList(Constants.promoImageList)

        val categoriesAdapter = CategoriesRvItemsAdapter(context)
        binding.categoriesRv.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL, false
        )
        binding.categoriesRv.adapter = categoriesAdapter

        val mealsAdapter = MealsRvItemsAdapter(context)
        binding.mealsRv.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL, false
        )
        binding.mealsRv.adapter = mealsAdapter

        categoriesAdapter.onCategoryClickListener = {
            if (viewModel.internetConnectionState.value == true) {
                viewModel.getMealsByCategory(it)
            } else {
                Snackbar.make(
                    binding.root, getString(R.string.no_internet_connection),
                    Snackbar.LENGTH_SHORT
                ).show()
                getMealsFromDb(mealsAdapter, context)
            }
        }

        observeDataFromSources(context, mealsAdapter, categoriesAdapter)
    }

    private fun observeCategoriesData(adapter: CategoriesRvItemsAdapter, context: Context) {
        viewModel.categoriesResponse.observe(viewLifecycleOwner) {
            if (it != null) {
                adapter.setCategoryList(it.categories)
            } else {
                val categoryNotFound = context.getString(R.string.no_category_found)
                adapter.setCategoryList(
                    listOf(
                        Category("", categoryNotFound, "", "")
                    )
                )
            }
        }
    }

    private fun observeMealsData(adapter: MealsRvItemsAdapter, context: Context) {
        viewModel.mealsByCategoryResponse.observe(viewLifecycleOwner) {
            if (it != null) {
                adapter.setMealsList(it.meals)
            } else {
                val mealNotFound = context.getString(R.string.no_meal_found)
                adapter.setMealsList(
                    listOf(
                        Meal("", mealNotFound, ""),
                        Meal("", mealNotFound, ""),
                        Meal("", mealNotFound, "")
                    )
                )
            }
        }
    }

    private fun getMealsFromDb(adapter: MealsRvItemsAdapter, context: Context) {
        lifecycleScope.launch {
            val meals = async(Dispatchers.IO) {
                viewModel.getMealsFromDb()
            }.await()
            if (meals.isNotEmpty()) {
                val mealsList = meals.map { myMeal ->
                    Meal("", myMeal.mealName, myMeal.mealThumb)
                }
                adapter.setMealsList(mealsList)
            } else {
                val mealNotFound = context.getString(R.string.no_meal_found)
                adapter.setMealsList(
                    listOf(
                        Meal("", mealNotFound, ""),
                        Meal("", mealNotFound, ""),
                        Meal("", mealNotFound, "")
                    )
                )
            }
        }
    }

    private fun getCategoriesFromDb(adapter: CategoriesRvItemsAdapter, context: Context) {
        lifecycleScope.launch {
            val categories = async(Dispatchers.IO) {
                viewModel.getCategoriesFromDb()
            }.await()

            if (categories.isNotEmpty()) {
                val categoriesList = categories.map { myCategory ->
                    Category("", myCategory.categoryName, "", "")
                }
                adapter.setCategoryList(categoriesList)
            } else {
                val categoryNotFound = context.getString(R.string.no_category_found)
                adapter.setCategoryList(
                    listOf(
                        Category("", categoryNotFound, "", "")
                    )
                )
            }
        }

    }

    private fun observeDataFromSources(
        context: Context, mealsRvItemsAdapter: MealsRvItemsAdapter,
        categoriesRvItemsAdapter: CategoriesRvItemsAdapter
    ) {
        viewModel.internetConnectionState.observe(viewLifecycleOwner) {
            if (it == false) {
                getMealsFromDb(mealsRvItemsAdapter, context)
                getCategoriesFromDb(categoriesRvItemsAdapter, context)
            } else {
                observeCategoriesData(categoriesRvItemsAdapter, context)
                observeMealsData(mealsRvItemsAdapter, context)
            }
        }
    }

}