package com.demo.demo.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.demo.demo.R
import com.demo.demo.adapter.ShoppingCategoryAdapter
import com.demo.demo.databinding.ActivityMainBinding
import com.demo.demo.model.ShoppingItemData
import com.demo.demo.model.ShoppingListData
import com.demo.demo.room.ShoppingDataEntity
import com.demo.demo.utils.CustomBottomDialog
import com.demo.demo.utils.OnItemClick
import com.demo.demo.viewmodel.LocalDataVM
import com.demo.demo.viewmodel.ShoppingListVM
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeMainActivity : AppCompatActivity(), OnItemClick {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: ShoppingListVM
    private lateinit var localDataVM: LocalDataVM
    private var count: Int = 0
    private var currentCount: Int = 0
    private val TAG = "HomeMainActivity Result "
    var localFavoriteDataEntity: ArrayList<ShoppingDataEntity> = ArrayList()
    var shoppingListData: ArrayList<ShoppingListData> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        viewModel = ViewModelProvider(this)[ShoppingListVM::class.java]
        localDataVM = ViewModelProvider(this)[LocalDataVM::class.java]
        getAllShoppingData()
        getJsonListData()
        handleClickListener()
    }

    private fun getJsonListData() {
        viewModel.getJsonData().observe(this) { jsonData ->
            // Use jsonData as needed
            Log.d(TAG, jsonData.toString())
            shoppingListData = jsonData.categories as ArrayList<ShoppingListData>
            loadShoppingList(jsonData.categories)
        }
    }

    private fun loadShoppingList(list: List<ShoppingListData>?) {
        val adapter = ShoppingCategoryAdapter(
            this, this, list as ArrayList<ShoppingListData>, localFavoriteDataEntity
        )
        binding.rvCategory.adapter = adapter
    }

    private fun saveData(
        item_id: String,
        name: String,
        icon: String,
        price: Double,
        item_count: Int,
        type: Int,
    ) {
        val data = ShoppingDataEntity(0, item_id, name, icon, price, item_count, type)
        try {
            val result = localDataVM.insertData(data)
            handleAddResult(result)
        } catch (e: Exception) {
            Toast.makeText(applicationContext, "Error while adding item", Toast.LENGTH_SHORT)
                .show();
        }
    }

    private fun handleAddResult(result: LocalDataVM.ResultWrapper<Unit>) {
        when (result) {
            is LocalDataVM.ResultWrapper.Success -> {
                // Handle successful result
                getAllShoppingData()
                getJsonListData()
            }
            is LocalDataVM.ResultWrapper.Error -> {
                // Handle Error result
                val error = result.exception
                Toast.makeText(applicationContext, " $error", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private fun getAllShoppingData() {
        // Observe changes in the LiveData
        localDataVM.getAllShoppingData().observe(this) { shoppingDataList ->
            setCartCount(shoppingDataList as ArrayList<ShoppingDataEntity>)
            localFavoriteDataEntity = shoppingDataList
            Log.d(TAG, "getAllShoppingData:Response  " + shoppingDataList)
        }
    }

    private fun setCartCount(shoppingDataEntity: ArrayList<ShoppingDataEntity>) {
        count = 0
        for (item in shoppingDataEntity) {
            if (item.type == 2) {
                count += 1
            }
        }
        binding.tvCount.text = "$count"
    }

    private fun deleteItem(item_id: Int, type: Int) {
        try {
            val result = localDataVM.deleteItem(item_id, type)
            handleAddResult(result)
        } catch (e: Exception) {
            Toast.makeText(applicationContext, "Error while adding item", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun updateRecord(item_count: Int, uid: Int) {
        try {
            val result = localDataVM.updateRecord(item_count, uid)
            handleAddResult(result)
        } catch (e: Exception) {
            Toast.makeText(applicationContext, "Error while adding item", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun handleClickListener() {
        binding.ivFavorite.setOnClickListener {
            goListingPage(1) // 1 For Favorite List
        }

        binding.rlCart.setOnClickListener {
            goListingPage(2) // 2 for Cart list
        }

        binding.ivSideMenu.setOnClickListener {
            val customBottomDialog =
                CustomBottomDialog(this, shoppingListData) // "this" is your activity context
            customBottomDialog.show()
        }
    }

    private fun goListingPage(type: Int) {
        val intent = Intent(this, ListingActivity::class.java)
        intent.putExtra("type", type)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left)
    }

    override fun onClick(data: ShoppingItemData, clickType: Int) {
        if (clickType == 1) {
            for (item in localFavoriteDataEntity) {
                if (item.item_id?.toInt() == data.id && item.type == 1) {
                    deleteItem(data.id!!.toInt(), clickType)
                    return
                }
            }
            saveData(data.id.toString(), data.name!!, data.icon!!, data.price!!, 1, clickType)
        } else {
            for (item in localFavoriteDataEntity) {
                if (item.item_id?.toInt() == data.id && item.type == 2) {
                    currentCount = item.item_count?.plus(1) ?: 0
                    updateRecord(currentCount, item.uid)
                    return
                }
            }
            saveData(data.id.toString(), data.name!!, data.icon!!, data.price!!, 1, clickType)
        }
    }

    override fun onClick(data: ShoppingDataEntity, clickType: Int) {

    }
}