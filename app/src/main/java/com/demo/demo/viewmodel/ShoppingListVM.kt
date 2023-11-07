package com.demo.demo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.demo.model.ShoppingListModel
import com.demo.demo.repository.ShoppingListRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


// View Model to get response from repository
@HiltViewModel
class ShoppingListVM @Inject constructor(private var shoppingListRepo: ShoppingListRepo) :
    ViewModel() {

    private val jsonData: MutableLiveData<ShoppingListModel> = MutableLiveData()

    fun getJsonData(): LiveData<ShoppingListModel> {
        viewModelScope.launch {
            val data = shoppingListRepo.loadJsonData()
            jsonData.postValue(data)
        }
        return jsonData
    }
}
