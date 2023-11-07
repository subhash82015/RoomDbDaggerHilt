package com.demo.demo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.demo.demo.databinding.BottomItemBinding
import com.demo.demo.model.ShoppingListData


class BottomItemAdapter(
    private val context: Context,
    private val list: ArrayList<ShoppingListData> = ArrayList<ShoppingListData>(),
) :
    RecyclerView.Adapter<BottomItemAdapter.ViewHolder>() {
    private lateinit var binding: BottomItemBinding
    var initCount: Int = 0

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        binding = BottomItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return BottomItemAdapter.ViewHolder(binding.root)

    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        binding.tvHeading.text = list[position].name ?: ""
    }


    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

    }


}
