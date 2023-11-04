package com.example.fooddeliverydemoproject.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fooddeliverydemoproject.R
import com.example.fooddeliverydemoproject.databinding.MealsRvItemBinding
import com.example.fooddeliverydemoproject.data_source.retrofit.Meal

class MealsRvItemsAdapter(private val context: Context): RecyclerView.Adapter<MealsRvItemsAdapter.ViewHolder>() {

    private var meals: List<Meal> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = MealsRvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    fun setMealsList(meals: List<Meal>) {
        this.meals = meals
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return meals.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val meal = meals[position]
        holder.mealName.text = meal.strMeal

        if (meal.strMeal == context.getString(R.string.no_meal_found)) {
            holder.priceTv.text = context.getString(R.string.price_not_available)
        } else {
            holder.priceTv.text = context.getString(R.string.price)
        }
        Glide.with(holder.itemView)
            .load(meal.strMealThumb)
            .placeholder(ContextCompat.getDrawable(context, R.drawable.meal_placeholder))
            .into(holder.image)
    }

    class ViewHolder(binding: MealsRvItemBinding): RecyclerView.ViewHolder(binding.root) {
        val image = binding.mealImage
        val mealName = binding.mealNameTv
        val priceTv = binding.mealPriceTv
    }
}