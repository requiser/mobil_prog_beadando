package com.example.mobil_prog_beadando.database.trip

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TripDao {
    @Query("select * from trip")
    fun getAll(): Flow<List<Trip>>

    @Query("select * from trip where id = :id")
    fun getTRipById(id: Int): Flow<List<Trip>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun upsert(trip: Trip)

    @Delete
    fun deleteTrip(trip: Trip)
}