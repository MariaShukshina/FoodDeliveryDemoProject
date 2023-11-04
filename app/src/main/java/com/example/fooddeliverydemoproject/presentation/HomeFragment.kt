package com.example.fooddeliverydemoproject.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fooddeliverydemoproject.R
import com.example.fooddeliverydemoproject.databinding.FragmentHomeBinding
import com.example.fooddeliverydemoproject.presentation.utils.Constants
import com.example.fooddeliverydemoproject.data_source.retrofit.Category
import com.example.fooddeliverydemoproject.data_source.retrofit.Meal
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private val viewModel by viewModel<HomeFragmentViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val context = requireContext()

        val bannersAdapter = BannersRvItemsAdapter()
        binding.bannersRv.layoutManager = LinearLayoutManager(context,
            LinearLayoutManager.HORIZONTAL, false)
        binding.bannersRv.adapter = bannersAdapter
        bannersAdapter.setImageList(Constants.promoImageList)

        val categoriesAdapter = CategoriesRvItemsAdapter(context)
        binding.categoriesRv.layoutManager = LinearLayoutManager(context,
            LinearLayoutManager.HORIZONTAL, false)
        binding.categoriesRv.adapter = categoriesAdapter

        val mealsAdapter = MealsRvItemsAdapter(context)
        binding.mealsRv.layoutManager = LinearLayoutManager(context,
            LinearLayoutManager.VERTICAL, false)
        binding.mealsRv.adapter = mealsAdapter

        categoriesAdapter.onCategoryClickListener = {
            if (viewModel.internetConnectionState.value == true) {
                viewModel.getMealsByCategory(it)
            } else {
                Snackbar.make(binding.root, getString(R.string.no_internet_connection),
                    Snackbar.LENGTH_SHORT).show()
                observeMealsFromDb(mealsAdapter, context)
            }
        }

        observeCategoriesData(categoriesAdapter, context)

        observeMealsData(mealsAdapter, context)

        observeDataFromDb(context, mealsAdapter, categoriesAdapter)


    }

    private fun observeCategoriesData(adapter: CategoriesRvItemsAdapter, context: Context) {
        viewModel.categoriesResponse.observe(viewLifecycleOwner) {
            if (it != null) {
                adapter.setCategoryList(it.categories)
            } else {
                val categoryNotFound = context.getString(R.string.no_category_found)
                adapter.setCategoryList(listOf(
                    Category("", categoryNotFound, "", "")
                ))
            }
        }
    }

    private fun observeMealsData(adapter: MealsRvItemsAdapter, context: Context) {
        viewModel.mealsByCategoryResponse.observe(viewLifecycleOwner) {
            if (it != null) {
                adapter.setMealsList(it.meals)
            } else {
                val mealNotFound = context.getString(R.string.no_meal_found)
                adapter.setMealsList(listOf(
                    Meal("", mealNotFound, ""),
                    Meal("", mealNotFound, ""),
                    Meal("", mealNotFound, "")
                ))
            }
        }
    }

    private fun observeMealsFromDb(adapter: MealsRvItemsAdapter, context: Context) {
        viewModel.mealsFromDb.observe(viewLifecycleOwner) {
            if (it != null) {
                val mealsList = it.map { myMeal ->
                    Meal("", myMeal.mealName, myMeal.mealThumb)
                }
                adapter.setMealsList(mealsList)
            } else {
                val mealNotFound = context.getString(R.string.no_meal_found)
                adapter.setMealsList(listOf(
                    Meal("", mealNotFound, ""),
                    Meal("", mealNotFound, ""),
                    Meal("", mealNotFound, "")
                ))
            }
        }
    }

    private fun observeCategoriesFromDb(adapter: CategoriesRvItemsAdapter, context: Context) {
        viewModel.categoriesFromDb.observe(viewLifecycleOwner) {
            if (it != null) {
                val categoriesList = it.map { myCategory ->
                    Category("", myCategory.categoryName, "", "")
                }
                adapter.setCategoryList(categoriesList)
            } else {
                val categoryNotFound = context.getString(R.string.no_category_found)
                adapter.setCategoryList(listOf(
                    Category("", categoryNotFound, "", "")
                ))
            }
        }
    }

    private fun observeDataFromDb(context: Context, mealsRvItemsAdapter: MealsRvItemsAdapter,
                                  categoriesRvItemsAdapter: CategoriesRvItemsAdapter){
        viewModel.internetConnectionState.observe(viewLifecycleOwner) {
            if (it == false) {
                observeMealsFromDb(mealsRvItemsAdapter, context)
                observeCategoriesFromDb(categoriesRvItemsAdapter, context)
            }
        }
    }

}