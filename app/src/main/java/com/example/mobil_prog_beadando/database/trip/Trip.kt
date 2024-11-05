package com.example.mobil_prog_beadando.database.trip

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Trip(
    @PrimaryKey val id: UUID,
    @ColumnInfo(name = "name")val name: String,
    @ColumnInfo(name = "goal")val goal: Float,
    @ColumnInfo(name = "cost")val cost: Float,
    @ColumnInfo(name = "difference")val difference: Float,
    )
