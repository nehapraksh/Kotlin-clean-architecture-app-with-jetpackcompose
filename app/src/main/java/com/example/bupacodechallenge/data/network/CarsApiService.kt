package com.example.bupacodechallenge.data.network

import com.example.bupacodechallenge.model.Car
import retrofit2.Response
import retrofit2.http.GET

/**
 * Cars api service
 *
 * @constructor Create empty Cars api service
 */
interface CarsApiService {

    /**
     * Get cars - get the car list from the api
     *
     * @return
     */
    @GET("/cars.json")
    suspend fun getCars(): Response<List<Car>>

}