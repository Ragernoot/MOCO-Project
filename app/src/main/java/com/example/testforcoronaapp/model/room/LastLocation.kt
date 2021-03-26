package com.example.testforcoronaapp.model.room

import androidx.room.Entity

// Brauchen wir das ?

@Entity
data class LastLocation (

    val latitude : Double,
    val longitude : Double

)