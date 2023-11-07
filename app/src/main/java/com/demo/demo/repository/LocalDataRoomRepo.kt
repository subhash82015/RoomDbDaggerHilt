package com.demo.demo.repository

import com.demo.demo.room.ShoppingDataDao
import com.demo.demo.room.ShoppingDataEntity
import javax.inject.Inject


//Repository class to to provide Response result to View Model
class LocalDataRoomRepo @Inject constructor(
    private val shoppingDataDao: ShoppingDataDao
) {
    fun saveLocalData(shoppingDataEntity: ShoppingDataEntity) {
        shoppingDataDao.insertAll(shoppingDataEntity)
    }

    fun getAllShoppingData(): List<ShoppingDataEntity> {
        return shoppingDataDao.getAll()
    }

    fun deleteItem(item_id: Int, type: Int) {
        shoppingDataDao.delete(item_id, type)
    }

    fun updateRecord(item_count: Int, uid: Int) {
        shoppingDataDao.updateRecord(item_count, uid)
    }

}
