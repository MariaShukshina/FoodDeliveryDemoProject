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
import com.example.fooddeliverydemoproject.retrofit.Category
import com.example.fooddeliverydemoproject.retrofit.Meal
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
            viewModel.getMealsByCategory(it)
        }

        observeCategoriesData(categoriesAdapter, context)

        observeMealsData(mealsAdapter, context)
    }

    private fun observeCategoriesData(adapter: CategoriesRvItemsAdapter, context: Context) {
        viewModel.categoriesResponse.observe(viewLifecycleOwner) {
            if (it != null) {
                adapter.setCategoryList(it.categories)
            } else {
                val categoryNotFound = context.getString(R.string.no_category_found)
                adapter.setCategoryList(listOf(
                    Category("", categoryNotFound, "", "")))
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

}