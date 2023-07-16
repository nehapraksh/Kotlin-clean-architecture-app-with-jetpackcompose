package com.example.bupacodechallenge.data.repository

import com.example.bupacodechallenge.MockResponseFileReader
import com.example.bupacodechallenge.data.datasource.CarsDataSource
import com.example.bupacodechallenge.data.network.BaseApiResponse
import com.example.bupacodechallenge.data.network.CarsApiService
import com.example.bupacodechallenge.data.network.NetworkResult
import com.example.bupacodechallenge.model.Car
import com.google.gson.GsonBuilder
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response

/**
 * Cars repository test
 *
 */
@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class CarsRepositoryTest : BaseApiResponse() {

    lateinit var carRepository: CarsRepository

    @Mock
    lateinit var apiService: CarsApiService

    @Mock
    lateinit var carsDataSource: CarsDataSource

    private var emptyCarList: List<Car> = emptyList()
    private lateinit var mockCarList: List<Car>

    /**
     * Setup
     *
     */
    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        carsDataSource = CarsDataSource(apiService)
        carRepository = CarsRepository(carsDataSource)
        mockData()
    }


    // test success response with valid car list from api call
    @Test
    fun test_GetCars_List() = runTest {
        Mockito.`when`(apiService.getCars()).thenReturn(
            Response.success(200,
                mockCarList
            ))
        val response = carRepository.getCarsResponse()
        assertEquals(true,  response is NetworkResult.Success)
        assertEquals(200,  Response.success(response).code())
        assertEquals(20,  response.data!!.size)
    }

    /**
     * Test_get assets_empty list - test success response with empty list from api call
     *
     */
    @Test
    fun test_GetCars_EmptyList() = runTest {
        Mockito.`when`(apiService.getCars()).thenReturn(
            Response.success(200,
            emptyCarList
        ))
        val response = carRepository.getCarsResponse()
        assertEquals(true,  response is NetworkResult.Success)
        assertEquals(200,  Response.success(response).code())
        assertEquals(0,  response.data!!.size)
    }

    /**
     * Test_get assets_error - test failure response from api call
     *
     */
    @Test
    fun test_GetCars_Error() = runTest {
        Mockito.`when`(apiService.getCars()).thenReturn(Response.error(403,"Forbidden".toResponseBody()))
        val response = carRepository.getCarsResponse()
        assertEquals(true,  response is NetworkResult.Error)
    }


    private fun mockData() {
        val gson = GsonBuilder().create()
        val cars: Array<Car> = gson.fromJson(
            MockResponseFileReader("cars.json").content,
            Array<Car>::class.java
        )
        mockCarList = cars.toList()
}

}