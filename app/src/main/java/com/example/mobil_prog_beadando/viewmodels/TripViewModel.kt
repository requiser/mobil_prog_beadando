package com.example.mobil_prog_beadando.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mobil_prog_beadando.database.trip.Trip
import com.example.mobil_prog_beadando.database.trip.TripDao
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import java.lang.IllegalArgumentException

class TripViewModel(
    private val tripDao: TripDao
): ViewModel(){
    fun getTrips(): Flow<List<Trip>> = tripDao.getAll()

    suspend fun upsert(item: Trip) = coroutineScope{
        launch(Dispatchers.IO){
            tripDao.upsert(item)
        }
    }

    suspend fun deleteTrip(item: Trip) = coroutineScope{
        launch(Dispatchers.IO){
            tripDao.deleteTrip(item)
        }
    }
}

class TripViewModelFactory(private val tripDao: TripDao): ViewModelProvider.Factory{
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(TripViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return TripViewModel(tripDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}