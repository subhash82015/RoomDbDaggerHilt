package com.demo.demo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.demo.demo.R
import com.demo.demo.databinding.CartItemBinding
import com.demo.demo.room.ShoppingDataEntity
import com.demo.demo.utils.OnItemClick


class LocalCartListIAdapter(
    private val context: Context,
    private val onItemClick: OnItemClick,
    private val list: ArrayList<ShoppingDataEntity> = ArrayList<ShoppingDataEntity>(),
) :
    RecyclerView.Adapter<LocalCartListIAdapter.ViewHolder>() {
    private lateinit var binding: CartItemBinding
    var initCount: Int = 0

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        binding = CartItemBinding.inflate(
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
        holder.binding.tvUnit.text = "${list[position].item_count} Unit"

        holder.binding.tvCount.text = "${list[position].item_count ?: 0}"

        val a: Double = (list[position].item_count ?: 0).toDouble()
        val b: Double = (list[position].price ?: 0).toDouble()

        holder.binding.tvPrice.text = "₹ ${a * b}"

        //In case of image null we are setting image as default
        if (list[position].icon != null) {
            Glide.with(context).load(list[position].icon)
                .into(holder.binding.ivCoverImage)
        } else {
            Glide.with(context)
                .load("").placeholder(R.drawable.cover_placeholder)
                .into(holder.binding.ivCoverImage)
        }

        holder.binding.tvAdd.setOnClickListener {
            onItemClick.onClick(list[position], 1)
        }

        holder.binding.tvSub.setOnClickListener {
            onItemClick.onClick(list[position], 0)
            //handleCount(holder, position, 0)
        }

    }

    private fun handleCount(holder: ViewHolder, position: Int, count: Int) {
        if (count == 0) {
            if (list[position].item_count!! >= 1) {
                initCount = list[position].item_count ?: 0
                val a: Int = initCount - 1
                val b: Int = (list[position].price ?: 0).toInt()

                holder.binding.tvPrice.text = "₹ ${a * b}"
                holder.binding.tvCount.text = "$a"
            }
        } else if (count == 1) {
            initCount = list[position].item_count ?: 0
            val a: Int = initCount + 1
            val b: Int = (list[position].price ?: 0).toInt()

            holder.binding.tvPrice.text = "₹ ${a * b}"
            holder.binding.tvCount.text = "$a"

        }
    }

    class ViewHolder(val binding: CartItemBinding) : RecyclerView.ViewHolder(binding.root) {

    }

}
