package com.demo.demo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.demo.model.ShoppingListModel
import com.demo.demo.repository.LocalDataRoomRepo
import com.demo.demo.room.ShoppingDataEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


// Local Room Database View Model to get response from repository
@HiltViewModel
class LocalDataVM @Inject constructor(private val localDataRoomRepo: LocalDataRoomRepo) :
    ViewModel() {


    fun insertData(shoppingDataEntity: ShoppingDataEntity): ResultWrapper<Unit> {
        return try {
            localDataRoomRepo.saveLocalData(shoppingDataEntity)
            ResultWrapper.Success(Unit)
        } catch (e: Exception) {
            ResultWrapper.Error(e)
        }
    }

    private val shoppingDataEntity: MutableLiveData<List<ShoppingDataEntity>> = MutableLiveData()

    fun getAllShoppingData(): LiveData<List<ShoppingDataEntity>> {
        val list = localDataRoomRepo.getAllShoppingData()
        shoppingDataEntity.postValue(list)
        return shoppingDataEntity
    }


    fun deleteItem(item_id: Int, type: Int): ResultWrapper<Unit> {
        return try {
            localDataRoomRepo.deleteItem(item_id, type)
            ResultWrapper.Success(Unit)
        } catch (e: Exception) {
            ResultWrapper.Error(e)
        }
    }

    fun updateRecord(item_count: Int, uid: Int): ResultWrapper<Unit> {
        return try {
            localDataRoomRepo.updateRecord(item_count, uid)
            ResultWrapper.Success(Unit)
        } catch (e: Exception) {
            ResultWrapper.Error(e)
        }
    }


    sealed class ResultWrapper<out T> {
        data class Success<out T>(val data: T) : ResultWrapper<T>()
        data class Error(val exception: Exception) : ResultWrapper<Nothing>()
    }
}



