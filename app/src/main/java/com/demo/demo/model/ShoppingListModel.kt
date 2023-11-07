package com.demo.demo.model


//Model class to get response from JSON File
data class ShoppingListModel(
    val status: String? = null,
    val message: String? = null,
    val error: String? = null,
    val categories: List<ShoppingListData>? = null,
)

data class ShoppingListData(
    val id: Int? = null,
    val name: String? = null,
    var isShow: Boolean = true,
    val items: List<ShoppingItemData>? = null,
)

data class ShoppingItemData(
    val id: Int? = null,
    val name: String? = null,
    val icon: String? = null,
    val price: Double? = null,
)