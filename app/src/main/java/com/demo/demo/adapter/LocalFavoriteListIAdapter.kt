package com.demo.demo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.demo.demo.R
import com.demo.demo.databinding.FavoriteItemBinding
import com.demo.demo.room.ShoppingDataEntity
import com.demo.demo.utils.OnItemClick


class LocalFavoriteListIAdapter(
    private val context: Context,
    private val onItemClick: OnItemClick,
    private val list: ArrayList<ShoppingDataEntity> = ArrayList<ShoppingDataEntity>(),
) :
    RecyclerView.Adapter<LocalFavoriteListIAdapter.ViewHolder>() {
    private lateinit var binding: FavoriteItemBinding
    var i: Int = 0

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        binding = FavoriteItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
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
        setImage(holder, position)
    }

    private fun setImage(holder: ViewHolder, position: Int) {
        //In case of image null we are setting image as default
        if (list[position].icon != null) {
            Glide.with(context).load(list[position].icon)
                .into(holder.binding.ivCoverImage)
        } else {
            Glide.with(context)
                .load("").placeholder(R.drawable.cover_placeholder)
                .into(holder.binding.ivCoverImage)
        }
    }

    class ViewHolder(val binding: FavoriteItemBinding) : RecyclerView.ViewHolder(binding.root) {
    }

}
