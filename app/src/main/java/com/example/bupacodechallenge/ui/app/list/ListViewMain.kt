package com.example.bupacodechallenge.ui.app.list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ProcessLifecycleOwner.Companion.get
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.bupacodechallenge.R
import com.example.bupacodechallenge.model.Car
import com.example.bupacodechallenge.viemodel.CarViewModel
import java.util.Random


/**
 * Main list - Composable component to set the view on the Home Screen
 *
 * @param navController
 * @param mainViewModel
 */
@ExperimentalFoundationApi
@Composable
fun MainList(
    navController: NavHostController, mainViewModel: CarViewModel
) {
    // set the view if the api call is in the loading state
    if (mainViewModel.loading) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
        }
    } else if (mainViewModel.errorMessage.isNotEmpty()) { // set the view if the api response data is set to error
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = mainViewModel.errorMessage,
                style = MaterialTheme.typography.subtitle1,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(8.dp)
            )
        }
    } else // set the view if the api response is success
        CarList(
            navController = navController,
            carList = mainViewModel.trendingCars,
            onItemClicked = mainViewModel::itemClicked
        )
}

/**
 * Car list - Composable component to set items view on the Main/Home screen
 *
 * @param navController
 * @param carList
 * @param onItemClicked
 * @receiver
 */
@ExperimentalFoundationApi
@Composable
fun CarList(
    navController: NavController,
    carList: List<Car>,
    onItemClicked: (item: Car, background: Color) -> Unit
) {
    val listState = rememberLazyListState()
    val Red = Color(red = 35, green = 61, blue = 83)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.text_home)) },
                backgroundColor = MaterialTheme.colors.background,
                contentColor = colorResource(id = R.color.text),
            )
        },
        content = {
            it
            LazyColumn(state = listState) {
                // add items to the list
                itemsIndexed(carList) { index, item ->
                    val backgroundColor = Color(Random().nextInt(204), Random().nextInt(227), Random().nextInt(198), 66)
//                        if (index % 2 == 0) Color(red = 204, green = 227, blue = 198) else Color(
//                            red = 234, green = 209, blue = 220
//                        )
                    ListViewItem(
                        navController = navController,
                        carItem = item,
                        onItemClicked,
                        backgroundColor
                    )
                }
            }
        }
    )


}


/**
 * Main header - Composable component to set the header/toolbar of the screen
 *
 * @param header
 */
@Composable
fun MainHeader(header: String) {
    Surface(
        Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.background)
    ) {
        Text(
            text = header,
            style = MaterialTheme.typography.h6,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(8.dp)
        )
    }
}