package com.example.mobil_prog_beadando.database

import androidx.room.RoomDatabase
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import com.example.mobil_prog_beadando.database.shoppinglist.ShoppingDao
import com.example.mobil_prog_beadando.database.shoppinglist.ShoppingList
import com.example.mobil_prog_beadando.database.trip.Trip
import com.example.mobil_prog_beadando.database.trip.TripDao

@Database(
    entities = [ShoppingList::class, Trip::class],
    version = 1,
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun ShoppingDao(): ShoppingDao
    abstract fun TripDao(): TripDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "app_database"
                )
                    .build()
                INSTANCE = instance
                instance
            }
        }

    }
}