package com.demo.demo.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shopping_items")
data class ShoppingDataEntity(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo(name = "item_id") val item_id: String?,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "icon") val icon: String?,
    @ColumnInfo(name = "price") val price: Double?,
    @ColumnInfo(name = "item_count") val item_count: Int?,
    @ColumnInfo(name = "type") val type: Int?
)

