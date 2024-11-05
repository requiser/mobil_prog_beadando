package com.example.mobil_prog_beadando.database.shoppinglist

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface ShoppingDao {
    /**
     * Get all shopping items by trip
     */
    @Query("select * from ShoppingList where tripId = :tripId" )
    fun getShoppingItemsByTripId(tripId: UUID): Flow<List<ShoppingList>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(item: ShoppingList)

    @Delete
    fun deleteItem(item: ShoppingList)
}