package com.example.bupacodechallenge.ui.app.details

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.bupacodechallenge.R
import com.example.bupacodechallenge.model.Car
import com.example.bupacodechallenge.util.Constants


/**
 * Car details - Composable component to show the selected car details on the Detail screen with the custom background color
 *
 * @param carItem
 * @param clickedItemColor
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarDetails(navController: NavController, carItem: Car, clickedItemColor: Color) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("${carItem.make.manufacturer} ${carItem.make.model} (${carItem.year})") },
                backgroundColor = MaterialTheme.colors.background,
                contentColor = colorResource(id = R.color.text),
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp, 24.dp)
                            .clickable {
                                navController.navigateUp()
                            },
                        tint = colorResource(id = R.color.text)
                    )
                }
            )
        },
        content = {
            it
            DetailsView(carItem = carItem, clickedItemColor = clickedItemColor)
        }
    )
}

@Composable
fun DetailsView(carItem: Car, clickedItemColor: Color) {
    Column(modifier = Modifier.background(clickedItemColor)) {
        CarDetailsBanner(carItem = carItem)
        CarDetailsText(carItem = carItem)
    }
}

/**
 * Car details banner - Composable component to show the selected car image
 * as banner on the Detail Screen using Coil lib
 *
 * @param carItem
 */
@Composable
fun CarDetailsBanner(carItem: Car) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            model = ImageRequest.Builder(LocalContext.current)
                .data("${Constants.IMAGE_URL}${carItem.image}")
                .build(),
            contentDescription = "",
            placeholder = painterResource(id = R.drawable.ic_placeholder),
            error = painterResource(id = R.drawable.ic_error),
            alignment = Alignment.TopCenter
        )
    }
}

/**
 * Car details text - Composable component to show selected car details
 *
 * @param carItem
 */
@Composable
fun CarDetailsText(carItem: Car) {
    Column(modifier = Modifier.padding(10.dp)) {
        DetailText(text = "Price - $${carItem.price}")
        DetailText(text = "Color - ${carItem.color}")
        DetailText(text = "Origin - ${carItem.origin}")
        DetailText(text = "Body - ${carItem.configuration.cylinders}")
        DetailText(text = "Horsepower - ${carItem.configuration.horsepower}")
    }
}

@Composable
fun DetailText(text: String) {
    Text(
        text = text, Modifier.padding(top = 10.dp), fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    )
}