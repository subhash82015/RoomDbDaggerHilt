package com.demo.demo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.demo.demo.databinding.ShoppingCategoryItemBinding
import com.demo.demo.model.ShoppingItemData
import com.demo.demo.model.ShoppingListData
import com.demo.demo.room.ShoppingDataEntity
import com.demo.demo.utils.OnItemClick


class ShoppingCategoryAdapter(
    private val context: Context,
    private val onItemClick: OnItemClick,
    private val list: ArrayList<ShoppingListData> = ArrayList<ShoppingListData>(),
    private val localFavoriteDataEntity: ArrayList<ShoppingDataEntity>,
) :
    RecyclerView.Adapter<ShoppingCategoryAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ShoppingCategoryItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, pos: Int) {
        if (list[pos].isShow) {
            holder.binding.rvItemList.visibility = View.VISIBLE
        } else {
            holder.binding.rvItemList.visibility = View.GONE
        }

        holder.binding.tvCategory.text = list[pos].name ?: ""
        loadShoppingItem(
            onItemClick,
            context,
            list[pos].items,
            holder.binding.rvItemList,
            localFavoriteDataEntity
        )

        holder.binding.rlFoodHeading.setOnClickListener {
            handleCategoryClick(pos)
        }
    }


    private fun handleCategoryClick(position: Int) {
        list[position].isShow = !list[position].isShow
        notifyDataSetChanged()
    }


    private fun loadShoppingItem(
        onItemClick: OnItemClick,
        context: Context,
        list: List<ShoppingItemData>?,
        recyclerView: RecyclerView,
        localFavoriteDataEntity: ArrayList<ShoppingDataEntity>
    ) {
        val adapter =
            ShoppingListItemsAdapter(
                context,
                onItemClick,
                list as ArrayList<ShoppingItemData>,
                localFavoriteDataEntity
            )
        recyclerView.adapter = adapter
    }

    class ViewHolder(val binding: ShoppingCategoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }


}
