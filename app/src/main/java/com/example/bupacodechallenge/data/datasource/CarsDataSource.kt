package com.example.bupacodechallenge.data.datasource

import com.example.bupacodechallenge.data.network.CarsApiService
import javax.inject.Inject

/**
 * Cars data source
 *
 * @property carsApi
 * @constructor Create empty Cars data source
 */
class CarsDataSource @Inject constructor(private val carsApi: CarsApiService) {
    suspend fun getCars() = carsApi.getCars()
}