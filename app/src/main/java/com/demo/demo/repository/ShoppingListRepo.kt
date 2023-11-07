package com.demo.demo.repository

import android.content.Context
import com.demo.demo.model.ShoppingListModel
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject



//Repository class to to provide Response result to View Model
class ShoppingListRepo @Inject constructor(private val context: Context) {
    suspend fun loadJsonData(): ShoppingListModel {
        return withContext(Dispatchers.IO) {
            val inputStream = context.assets.open("shopping.json")
            val jsonString = inputStream.bufferedReader().use { it.readText() }
            val shoppingListModel: ShoppingListModel =
                Gson().fromJson(jsonString, ShoppingListModel::class.java)
            shoppingListModel
        }
    }

}
