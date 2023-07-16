package com.example.bupacodechallenge.model

/* *
* Data class to store Car data received from server
 * */
data class Car(
    val make: Make,
    val color: String,
    val year: Int,
    val configuration: Configuration,
    val origin: String,
    val mpg: Int,
    val image: String,
    val price: String
)
