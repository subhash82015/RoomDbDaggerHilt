package com.demo.demo.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.demo.demo.adapter.LocalCartListIAdapter
import com.demo.demo.adapter.LocalFavoriteListIAdapter
import com.demo.demo.databinding.ActivityListingBinding
import com.demo.demo.model.ShoppingItemData
import com.demo.demo.room.ShoppingDataEntity
import com.demo.demo.utils.OnItemClick
import com.demo.demo.viewmodel.LocalDataVM
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ListingActivity : AppCompatActivity(), OnItemClick {
    private lateinit var binding: ActivityListingBinding
    private lateinit var localDataVM: LocalDataVM
    private val TAG = "ListingActivity "

    private var type: Int = 0
    private var totalAmount: Int = 0

    private var localCartDataEntity: ArrayList<ShoppingDataEntity>? = null
    private var localFavoriteDataEntity: ArrayList<ShoppingDataEntity>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        localDataVM = ViewModelProvider(this)[LocalDataVM::class.java]
        handleClickListener()
        getAllShoppingData()
        getBundleData()
        setTitle()
    }

    private fun handleClickListener() {
        binding.ivBack.setOnClickListener {
            finish()
        }
    }

    private fun getBundleData() {
        type = intent?.getIntExtra("type", 0) ?: 0
    }

    private fun setTitle() {
        if (type == 1) {
            binding.tvTitle.text = "Favorite"
            handleVisibility()
        } else {
            binding.tvTitle.text = "Cart"
        }
    }

    private fun handleVisibility() {
        binding.rlBottomLayout.visibility = View.GONE
    }

    private fun getAllShoppingData() {
        // Observe changes in the LiveData
        localDataVM.getAllShoppingData().observe(this) { shoppingDataList ->
            Log.d(TAG, "getAllShoppingData:Response  " + shoppingDataList)
            loadFavoriteList(shoppingDataList)
        }
    }

    private fun loadFavoriteList(list: List<ShoppingDataEntity>?) {
        localFavoriteDataEntity = ArrayList()
        localCartDataEntity = ArrayList()
        if (list != null) {
            for (item in list) {
                if (item.type == 1) { // 1 is type for favorite
                    localFavoriteDataEntity!!.add(item)
                } else {
                    localCartDataEntity!!.add(item)
                    val shoppingItemsList: List<ShoppingDataEntity> =
                        localCartDataEntity as ArrayList<ShoppingDataEntity>
                    val total = calculateTotalPrice(shoppingItemsList)

                    totalAmount += item.price!!.toInt()
                    binding.tvSubTotalData.text = "$total"
                    binding.tvTotalData.text = "$total"
                }
            }
        }
        setAdapter()
    }


    // Calculate the sum of prices in a list of ShoppingDataEntity objects
    private fun calculateTotalPrice(shoppingList: List<ShoppingDataEntity>): Double {
        var totalPrice = 0.0
        for (item in shoppingList) {
            item.price?.times(item.item_count!!)?.let {
                totalPrice += it
            }
        }
        return totalPrice
    }

    private fun setAdapter() {
        if (type == 1) { // 1 is type for favorite
            val adapter = LocalFavoriteListIAdapter(this, this, localFavoriteDataEntity!!)
            binding.rvList.adapter = adapter
        } else {
            val adapter = LocalCartListIAdapter(this, this, localCartDataEntity!!)
            binding.rvList.adapter = adapter
        }

    }

    override fun onClick(data: ShoppingItemData, clickType: Int) {
    }

    override fun onClick(data: ShoppingDataEntity, clickType: Int) {
        when (clickType) {
            1 -> {
                updateRecord(data.item_count?.plus(1) ?: 0, data.uid)
            }
            0 -> {
                if (data.item_count!! == 1) {
                    return
                }
                updateRecord(data.item_count.minus(1) ?: 0, data.uid)
            }
        }
    }

    private fun updateRecord(item_count: Int, uid: Int) {
        try {
            val result = localDataVM.updateRecord(item_count, uid)
            handleAddResult(result)
        } catch (e: Exception) {
            Toast.makeText(applicationContext, "Error while adding item", Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleAddResult(result: LocalDataVM.ResultWrapper<Unit>) {
        when (result) {
            is LocalDataVM.ResultWrapper.Success -> {
                // Handle successful result
                localFavoriteDataEntity?.clear()
                localCartDataEntity?.clear()
                getAllShoppingData()
            }
            is LocalDataVM.ResultWrapper.Error -> {
                // Handle Error result
                val error = result.exception
                Toast.makeText(applicationContext, " $error", Toast.LENGTH_SHORT).show();
            }
        }
    }
}