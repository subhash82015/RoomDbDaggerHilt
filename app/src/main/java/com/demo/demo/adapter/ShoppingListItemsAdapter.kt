package com.demo.demo.adapter

import android.content.Context
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.demo.demo.R
import com.demo.demo.databinding.ListItemBinding
import com.demo.demo.model.ShoppingItemData
import com.demo.demo.room.ShoppingDataEntity
import com.demo.demo.utils.OnItemClick


class ShoppingListItemsAdapter(
    private val context: Context,
    private val onItemClick: OnItemClick,
    private val list: ArrayList<ShoppingItemData> = ArrayList<ShoppingItemData>(),
    private val localFavoriteDataEntity: ArrayList<ShoppingDataEntity>,
) : RecyclerView.Adapter<ShoppingListItemsAdapter.ViewHolder>() {
    private lateinit var binding: ListItemBinding
    var i: Int = 0

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ViewHolder {
        binding = ListItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)

    }

    override fun getItemCount(): Int {

        return list.size

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvTitle.text = list[position].name ?: ""
        holder.binding.tvUnit.text = "1 Unit"
        holder.binding.tvPrice.text = "₹ ${list[position].price}"

        handleFavoriteIcon(position, holder)
        setImage(holder, position)
        holder.binding.ivFavorite.setOnClickListener {
            handleAnimation(holder.binding.ivFavorite)
            onItemClick.onClick(list[position], 1)   // 1 for favorite click button
        }
        holder.binding.tvAdd.setOnClickListener {
            handleAnimation(holder.binding.tvAdd)
            onItemClick.onClick(list[position], 2)   // 2 for add item click button
        }
    }


    private fun setImage(holder: ViewHolder, position: Int) {
        //In case of image null we are setting image as default
        if (list[position].icon != null) {
            Glide.with(context).load(list[position].icon).into(holder.binding.ivCoverImage)
        } else {
            Glide.with(context).load("").placeholder(R.drawable.cover_placeholder)
                .into(holder.binding.ivCoverImage)
        }
    }

    private fun handleAnimation(view: View) {
        view.startAnimation(AnimationUtils.loadAnimation(context, R.anim.image_click))
    }

    private fun handleFavoriteIcon(position: Int, holder: ViewHolder) {
        for (item in localFavoriteDataEntity) {
            if (item.item_id?.toInt() == list[position].id && item.type != 2) {
                holder.binding.ivFavorite.setImageDrawable(
                    ContextCompat.getDrawable(
                        context, R.drawable.baseline_favorite_24
                    )
                )
                val tintColor = ContextCompat.getColor(context, R.color.red) // Get the color
                holder.binding.ivFavorite.setColorFilter(
                    tintColor, PorterDuff.Mode.SRC_IN
                ) // Apply the color filter
            }
        }
    }

    class ViewHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {

    }


}
