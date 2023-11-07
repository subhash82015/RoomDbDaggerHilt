package com.demo.demo.room
import androidx.room.*

@Dao
interface ShoppingDataDao {

    @Query("SELECT * FROM shopping_items")
    fun getAll(): List<ShoppingDataEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(users: ShoppingDataEntity)


    @Query("DELETE FROM shopping_items WHERE item_id = :item_id AND type = :type")
    fun delete(item_id: Int, type: Int)

    @Query("UPDATE shopping_items SET item_count = :item_count WHERE uid = :uid")
    fun updateRecord(item_count: Int, uid: Int)

}