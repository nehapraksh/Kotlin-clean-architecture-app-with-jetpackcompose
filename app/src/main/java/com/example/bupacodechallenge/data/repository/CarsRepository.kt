package com.example.bupacodechallenge.data.repository

import com.example.bupacodechallenge.data.datasource.CarsDataSource
import com.example.bupacodechallenge.data.network.BaseApiResponse
import com.example.bupacodechallenge.data.network.NetworkResult
import com.example.bupacodechallenge.model.Car
import javax.inject.Inject

/**
 * Cars repository
 *
 * @property carsDataSource
 * @constructor Create empty Cars repository
 */
class CarsRepository @Inject constructor(
    private val carsDataSource: CarsDataSource
) : BaseApiResponse() {

    /*
    * Method to get the data from api
    * It is to be called by coroutines , so declared as suspended
    * */
    suspend fun getCarsResponse(): NetworkResult<List<Car>> {
        return safeApiCall { carsDataSource.getCars() }
    }
}