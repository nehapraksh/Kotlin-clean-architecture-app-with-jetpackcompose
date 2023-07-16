package com.example.bupacodechallenge.data.network

import com.example.bupacodechallenge.MockResponseFileReader
import com.example.bupacodechallenge.model.Car
import com.example.bupacodechallenge.model.Configuration
import com.example.bupacodechallenge.model.Make
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.MockitoAnnotations
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class CarApiServiceTest {

    private lateinit var mockWebServer: MockWebServer
    lateinit var apiService: CarsApiService
    lateinit var gson: Gson
    private lateinit var carList: List<Car>

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        gson = GsonBuilder().setLenient().create()
        mockWebServer = MockWebServer()
        mockWebServer.start()
        apiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build().create(CarsApiService::class.java)
    }

    // test reading json file from the resource folder
    @Test
    fun `read sample success json file`(){
        val reader = MockResponseFileReader("cars.json")
        assertNotNull(reader.content)
    }


    // test valid api response from the server
    @Test
    fun `get car api test`() = runTest {
            val mockResponse = MockResponse()
            mockWebServer.enqueue(mockResponse.setBody(MockResponseFileReader("cars.json").content))
            val response = apiService.getCars()
            val request = mockWebServer.takeRequest()
            assertEquals("/cars.json",request.path)
            assertEquals(true, response.body()?.isEmpty() == false)
        }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }
}

