package com.example.bupacodechallenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bupacodechallenge.navigation.Screen
import com.example.bupacodechallenge.ui.app.details.CarDetails
import com.example.bupacodechallenge.ui.app.list.MainList
import com.example.bupacodechallenge.ui.theme.BupaCodeChallengeTheme
import com.example.bupacodechallenge.viemodel.CarViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * Main activity - entry point of the application
 *
 */
@OptIn(ExperimentalFoundationApi::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val carViewModel by viewModels<CarViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            BupaCodeChallengeTheme {
                // A surface container using the 'background' color from the theme
                // handle the navigation components and show the first screen as the Home/Main Screen
                NavHost(navController = navController, startDestination = Screen.Home.route) {
                    composable(Screen.Home.route) {
                        Surface(color = Color.LightGray) {
                            MainList(navController = navController, mainViewModel = carViewModel)
                        }
                    }
                    // define and handle the navigation to the Details screen when clicked on item from the Home Screen
                    composable(Screen.Details.route) {
                        CarDetails(navController = navController,carViewModel.clickedItem,carViewModel.clickedItemColor)
                    }
                }
            }
        }
    }
}