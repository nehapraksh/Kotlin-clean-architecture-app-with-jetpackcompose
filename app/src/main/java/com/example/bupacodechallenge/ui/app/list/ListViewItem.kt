package com.example.bupacodechallenge.ui.app.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.bupacodechallenge.R
import com.example.bupacodechallenge.model.Car
import com.example.bupacodechallenge.navigation.Screen
import com.example.bupacodechallenge.util.Constants


/**
 * List view item - Composable component to add items to the list on the Main screen and
 * to handle the click even on item to move to next screen
 *
 * @param navController
 * @param carItem
 * @param onItemClicked
 * @param background
 * @receiver
 */
@Composable
fun ListViewItem(
    navController: NavController,
    carItem: Car,
    onItemClicked: (item: Car, background: Color) -> Unit,
    background: Color
) {
    var selectedIndex by remember { mutableIntStateOf(-1) }
    ListViewItem(
        carItem = carItem, modifier = Modifier
            .padding(8.dp, 4.dp)
            .clickable {
                onItemClicked(carItem, background)
                navController.navigate(Screen.Details.route)
            }, background
    )
}

/**
 * List view item - Composable component to add cars to the list on the Main screen
 *
 * @param carItem
 * @param modifier
 * @param background
 */
@Composable
fun ListViewItem(
    carItem: Car, modifier: Modifier, background: Color
) {
    Card(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.background(background)
        ) {
            CarImageBanner(imagePath = carItem.image)
            CarMetadataItem(carItem = carItem)
        }
    }
}

/**
 * Car image banner - Composable component to add image of the each car in the list on the Main screen
 *
 * @param imagePath
 */
@Composable
fun CarImageBanner(imagePath: String?) {
    AsyncImage(
        modifier = Modifier
            .width(180.dp)
            .height(100.dp)
            .clip(RoundedCornerShape(4.dp)),
        model = ImageRequest.Builder(LocalContext.current)
            .data("${Constants.IMAGE_URL}$imagePath")
            .build(),
        contentDescription = "",
        placeholder = painterResource(id = R.drawable.ic_placeholder),
        error = painterResource(id = R.drawable.ic_error),
        alignment = Alignment.CenterStart
    )
}

/**
 * Car metadata item - Composable component to add details of each car in the list on the Main screen
 *
 * @param carItem
 */
@Composable
fun CarMetadataItem(carItem: Car) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 5.dp),
    ) {
        carItem.make.let {
            Text(
                text = "${it.manufacturer} ${it.model} (${carItem.year})"
            )
            Text(
                text = "Price - $${carItem.price}",
                style = MaterialTheme.typography.bodyMedium
            )
        }

    }
}

