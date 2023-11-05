
package com.example.fooddeliverydemoproject.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddeliverydemoproject.R
import com.example.fooddeliverydemoproject.databinding.CategoriesRvItemBinding
import com.example.fooddeliverydemoproject.data.data_source.retrofit.Category

class CategoriesRvItemsAdapter(private val context: Context): RecyclerView.Adapter<CategoriesRvItemsAdapter.ViewHolder>() {

    private var categories: List<Category> = listOf()
    private var selectedItem = 0
    lateinit var onCategoryClickListener: (String) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CategoriesRvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    fun setCategoryList(categories: List<Category>) {
        this.categories = categories
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = categories[position]
        holder.textView.text = category.strCategory
        holder.itemView.setOnClickListener {

            if (category.strCategory != context.getString(R.string.no_category_found))
            {
                onCategoryClickListener.invoke(category.strCategory)
            }

            val previousSelectedItem = selectedItem
            selectedItem = holder.adapterPosition
            notifyItemChanged(previousSelectedItem)
            notifyItemChanged(selectedItem)
        }
        if (category.strCategory != context.getString(R.string.no_category_found)) {
            if (selectedItem == position) {
                holder.textView.setTextColor(ContextCompat.getColor(context,
                    R.color.selected_icon_color))
                holder.itemView.background = ContextCompat.getDrawable(context,
                    R.drawable.category_selected_background)
            } else {
                holder.textView.setTextColor(ContextCompat.getColor(context,
                    R.color.default_category_text))
                holder.itemView.background = ContextCompat.getDrawable(context,
                    R.drawable.category_default_background)
            }
        }
    }

    class ViewHolder(binding: CategoriesRvItemBinding): RecyclerView.ViewHolder(binding.root) {
        val textView = binding.textView
    }
}