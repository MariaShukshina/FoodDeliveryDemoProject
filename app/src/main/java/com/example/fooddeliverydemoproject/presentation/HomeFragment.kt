package com.example.fooddeliverydemoproject.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
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

        val bannersAdapter = BannersRvItemsAdapter()
        binding.bannersRv.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.HORIZONTAL, false)
        binding.bannersRv.adapter = bannersAdapter
        bannersAdapter.setImageList(Constants.promoImageList)

        val categoriesAdapter = CategoriesRvItemsAdapter(requireContext())
        binding.categoriesRv.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.HORIZONTAL, false)
        binding.categoriesRv.adapter = categoriesAdapter

        val mealsAdapter = MealsRvItemsAdapter(requireContext())
        binding.mealsRv.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.VERTICAL, false)
        binding.mealsRv.adapter = mealsAdapter

        categoriesAdapter.onCategoryClickListener = {
            viewModel.getMealsByCategory(it)
        }

        observeCategoriesData(categoriesAdapter)

        observeMealsData(mealsAdapter)
    }

    private fun observeCategoriesData(adapter: CategoriesRvItemsAdapter) {
        viewModel.categoriesResponse.observe(viewLifecycleOwner) {
            if (it != null) {
                adapter.setCategoryList(it.categories)
            } else {
                adapter.setCategoryList(listOf(
                    Category("", "Pizza", "", ""),
                    Category("", "Pasta", "", ""),
                    Category("", "Seafood", "", ""),
                    Category("", "Beef", "", "")))
            }
        }
    }

    private fun observeMealsData(adapter: MealsRvItemsAdapter) {
        viewModel.mealsByCategory.observe(viewLifecycleOwner) {
            if (it != null) {
                adapter.setMealsList(it.meals)
            } else {
                adapter.setMealsList(listOf(
                    Meal("", "No meal found.", ""),
                    Meal("", "No meal found.", ""),
                    Meal("", "No meal found.", "")
                ))
            }
        }
    }

}