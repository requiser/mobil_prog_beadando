package com.example.mobil_prog_beadando.database.shoppinglist

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.mobil_prog_beadando.database.trip.Trip
import java.util.*

@Entity(
    foreignKeys = arrayOf(ForeignKey(
        entity = Trip::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("tripId"),
        onDelete = ForeignKey.CASCADE
        ))
)
data class ShoppingList(
    @PrimaryKey val id: UUID,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "amount") val amount: Int,
    @ColumnInfo(name = "price") val price: Float,
    @ColumnInfo(name = "tripId") val tripId: UUID,
    )
