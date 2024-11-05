package com.example.mobil_prog_beadando.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mobil_prog_beadando.database.shoppinglist.ShoppingDao
import com.example.mobil_prog_beadando.database.shoppinglist.ShoppingList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.*


class ShoppingListViewModel(private val shoppingDao: ShoppingDao): ViewModel() {

    fun getShoppingListByTrip(tripId: UUID): Flow<List<ShoppingList>> = shoppingDao.getShoppingItemsByTripId(tripId)

     suspend fun upsert(item: ShoppingList) = coroutineScope {
         launch(Dispatchers.IO){
             println(item)
             try {
                 shoppingDao.upsert(item)
             } catch (e: Exception){
                 println(e.stackTrace)
             }
         }
    }

    suspend fun deleteItem(item: ShoppingList) = coroutineScope {
        launch(Dispatchers.IO){
            shoppingDao.deleteItem(item)
        }
    }
}

class ShoppingListViewModelFactory(private val shoppingDao: ShoppingDao): ViewModelProvider.Factory{
    @Suppress("Null")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ShoppingListViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return ShoppingListViewModel(shoppingDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
