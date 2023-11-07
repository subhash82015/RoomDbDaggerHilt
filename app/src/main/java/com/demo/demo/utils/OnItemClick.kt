package com.demo.demo.utils

import com.demo.demo.model.ShoppingItemData
import com.demo.demo.room.ShoppingDataEntity

interface OnItemClick {
    fun onClick(data: ShoppingItemData, clickType: Int)
    fun onClick(data: ShoppingDataEntity, clickType: Int)
}