package com.example.bupacodechallenge.viemodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bupacodechallenge.data.network.NetworkResult
import com.example.bupacodechallenge.data.repository.CarsRepository
import com.example.bupacodechallenge.model.Car
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Car view model- ViewModel for the Main Activity
 *
 * @property repository
 *
 */
@HiltViewModel
class CarViewModel @Inject constructor(private val repository: CarsRepository) : ViewModel() {

    // class variables
    lateinit var clickedItem: Car
    var clickedItemColor: Color = Color(red = 243, green = 246, blue = 244 )

    // variables to store different response data after the api call
    var loading: Boolean by mutableStateOf(true)
    var errorMessage: String by mutableStateOf("")
    var trendingCars: List<Car> by mutableStateOf(emptyList())

    init {
        // call the api to fetch data
        fetchCarsFromApi()
    }


     /* *
     * function to retrieve data from the server using coroutine and
     * storing the data based on Success and Failure
     * */
     fun fetchCarsFromApi() {
        // launch the api call within the viewmodel scope
        viewModelScope.launch{
            when (val response = repository.getCarsResponse()) {
                // if the response is success
                is NetworkResult.Success -> {
                    // set the loading to false
                    loading = false
                    // store the success response data to the trending cars
                    trendingCars = response.data!!
                }
                // if the response is failure
                is NetworkResult.Error -> {
                    // set the loading to false
                    loading = false
                    // store the failure message
                    errorMessage = response.message.toString()

                }
            }
        }
    }

    /**
     * Item clicked - function to be used to move to the details screen from the main list
     *
     * @param item
     * @param background
     */
    fun itemClicked(item: Car, background: Color) {
        clickedItem = item
        clickedItemColor = background
    }


}
