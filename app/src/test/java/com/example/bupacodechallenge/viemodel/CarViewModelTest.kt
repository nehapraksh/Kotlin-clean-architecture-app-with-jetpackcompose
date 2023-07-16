package com.example.bupacodechallenge.viemodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.compose.ui.graphics.Color
import com.example.bupacodechallenge.MockResponseFileReader
import com.example.bupacodechallenge.data.network.NetworkResult
import com.example.bupacodechallenge.data.repository.CarsRepository
import com.example.bupacodechallenge.model.Car
import com.google.gson.GsonBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations


/**
 * Car view model test
 *
 */
@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class CarViewModelTest {

    // class variables declaration
    // test dispatcher to test the coroutine
    private val testDispatcher = StandardTestDispatcher()

    // view model
    private lateinit var viewModel: CarViewModel

    // mock the repository
    @Mock
    private lateinit var mockRepository: CarsRepository

    // empty car list for testing empty response
    private val emptyCarList: List<Car> = emptyList()

    // car list for testing success response car list
    private lateinit var mockCarList: List<Car>

    // set the rule
    @get:Rule
    val instantTaskExecutionRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    /**
     * Setup
     *
     */
    @Before
    fun setup() {
        // initialize the mockito component
        MockitoAnnotations.openMocks(this)
        // set the testDispatcher
        Dispatchers.setMain(testDispatcher)
        // initialise the view model
        viewModel = CarViewModel(mockRepository)
        // get the mock data from the json file
        mockData()
    }

    /**
     * Tear down
     *
     */
    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cancel()
    }

    // test the success response with correct car list from the api using the coroutine in view model
    @Test
    fun test_FetchDataFromApi_expectedSuccess() = runTest {
        Mockito.`when`(mockRepository.getCarsResponse())
            .thenReturn(NetworkResult.Success(mockCarList))
        viewModel.fetchCarsFromApi()
        // Await the change
        testDispatcher.scheduler.advanceUntilIdle()
        val result = viewModel.trendingCars
        assertEquals(viewModel.errorMessage, "")
        assertTrue(!viewModel.loading)
        assertEquals(result, mockCarList)
        assertEquals(20, result.size)
        assert(result[0].color == "Red")
        assert(result[1].year == 2020)
    }

    // test the success response with empty car list from the api using the coroutine in view model
    @Test
    fun `retrieve cars with ViewModel and Repository returns empty data`() = runTest {
        Mockito.`when`(mockRepository.getCarsResponse())
            .thenReturn(NetworkResult.Success(emptyCarList))
        viewModel.fetchCarsFromApi()
        // Await the change
        testDispatcher.scheduler.advanceUntilIdle()
        val result = viewModel.trendingCars
        assertEquals(viewModel.errorMessage, "")
        assertTrue(!viewModel.loading)
        assertTrue(result.isEmpty())
        assertEquals(0, result.size)
    }

    // test the error response from the api using the coroutine in view model
    @Test
    fun test_FetchDataFromApi_expectedError() = runTest {
        Mockito.`when`(mockRepository.getCarsResponse())
            .thenReturn(NetworkResult.Error("Api call failed"))
        viewModel.fetchCarsFromApi()
        // Await the change
        testDispatcher.scheduler.advanceUntilIdle()
        val result = viewModel.errorMessage
        assertNotEquals(viewModel.errorMessage, "")
        assertTrue(!viewModel.loading)
        assertTrue(viewModel.trendingCars.isEmpty())
        assertEquals("Api call failed", result)

    }

    @Test
    fun itemClicked() {
        val car = mockCarList[0]
        val backgroundColor = Color.Red

        viewModel.itemClicked(car, backgroundColor)

        assert(viewModel.clickedItem == car)
        assert(viewModel.clickedItemColor == backgroundColor)
    }


    // read data from the json file and set to the mock list
    private fun mockData() {
        val gson = GsonBuilder().create()

        val cars: Array<Car> = gson.fromJson(
            MockResponseFileReader("cars.json").content,
            Array<Car>::class.java
        )
        mockCarList = cars.toList()
    }
}
