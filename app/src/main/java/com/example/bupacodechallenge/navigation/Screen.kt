package com.example.bupacodechallenge.navigation

import androidx.annotation.StringRes
import com.example.bupacodechallenge.R

sealed class Screen(val route: String, @StringRes val resourceId: Int) {
    object Home : Screen("trendingCarList", R.string.text_home)
    object Details : Screen("carDetails", R.string.text_details)
}