package com.example.mobil_prog_beadando

import android.app.Application
import com.example.mobil_prog_beadando.database.AppDatabase

class ShoppingListApplication: Application() {
    val database: AppDatabase by lazy {AppDatabase.getDatabase(this)} }