package com.demo.demo.utils

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.view.Window
import com.demo.demo.adapter.BottomItemAdapter
import com.demo.demo.databinding.BottomLayoutBinding
import com.demo.demo.model.ShoppingListData

class CustomBottomDialog(context: Context, private val list: ArrayList<ShoppingListData>) :
    Dialog(context) {

    lateinit var binding: BottomLayoutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )
        window?.setGravity(Gravity.BOTTOM)
        window?.setBackgroundDrawableResource(android.R.color.transparent) // Set background transparent
        binding = BottomLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root) // Set your custom layout here

        val adapter = BottomItemAdapter(
            context, list
        )
        binding.rvBottomList.adapter = adapter

        binding.ibCancel.setOnClickListener {
            dismiss()
        }
    }
}